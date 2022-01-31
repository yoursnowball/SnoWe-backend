package com.snowman.project.notifications.service

import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import com.snowman.project.common.utils.push.PushUtil
import com.snowman.project.goals.dao.GoalRepository
import com.snowman.project.goals.service.exceptions.GoalNotExistException
import com.snowman.project.notifications.dao.PushRepository
import com.snowman.project.notifications.domain.dto.PushHistoryDto
import com.snowman.project.notifications.domain.dto.SendPushMessageDto
import com.snowman.project.notifications.domain.entity.PushMessage
import com.snowman.project.notifications.domain.enums.PushType
import com.snowman.project.notifications.service.exceptions.NotificationNotFoundException
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import com.snowman.project.users.service.exceptions.UserNotExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PushService(
    private val pushRepository: PushRepository,
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository
) {

    fun readAlarm(alarmId: Long): PushHistoryDto {
        val push = pushRepository.findByIdOrNull(alarmId) ?: throw NotificationNotFoundException()
        push.readAlarm()

        return PushHistoryDto(push)
    }

    @Throws(FirebaseMessagingException::class)
    fun sendPushMessage(userId: Long, goalId: Long, type: PushType) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotExistException()
        val goal = goalRepository.findByIdOrNull(goalId) ?: throw GoalNotExistException()
        var sendPushMessageDto: SendPushMessageDto? = null

        when (type) {
            PushType.LEVELUP -> {
                sendPushMessageDto = PushUtil.levelUpAlarm(goal.name, goal.level)
            }
            else -> throw NotificationNotFoundException()
        }
        sendPushMessageDto.let { message ->
            user.fcmToken?.let { fcmToken ->
                pushRepository.save(
                    PushMessage(
                        user = user,
                        title = message.title,
                        body = message.body
                    )
                )
                sendIOS(listOf(fcmToken), message)
            }
        }
    }

    @Throws(FirebaseMessagingException::class)
    fun sendPushMessages(userList: List<User>, type: PushType) {
        val tokenList = userList.mapNotNull { it.fcmToken }
        var sendPushMessageDto: SendPushMessageDto? = null

        when (type) {
            PushType.WRITE -> {
                sendPushMessageDto = PushUtil.todoWriteAlarm()
            }
            PushType.CHEERUP -> {
                sendPushMessageDto = PushUtil.getCheerUpAlarm()
            }
            PushType.DAILY -> {
                sendPushMessageDto = PushUtil.dailyMorningAlarm()
            }
        }
        sendPushMessageDto?.let { message ->
            userList.filter { user -> user.fcmToken != null }
                .forEach { filteredUser ->
                    pushRepository.save(
                        PushMessage(
                            user = filteredUser,
                            title = message.title,
                            body = message.body
                        )
                    )
                }
            sendIOS(tokenList, message)
        }
    }

    private fun sendIOS(tokenList: List<String>, sendPushMessageDto: SendPushMessageDto): Int {
        return if (tokenList.isNotEmpty()) {
            val multicast = MulticastMessage.builder()
                .setNotification(Notification(sendPushMessageDto.title, sendPushMessageDto.body))
                .putData("title", sendPushMessageDto.title)
                .addAllTokens(tokenList)
                .setApnsConfig(
                    ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).build()
                ).build()
            FirebaseMessaging.getInstance().sendMulticast(multicast).successCount
        } else
            0
    }
}