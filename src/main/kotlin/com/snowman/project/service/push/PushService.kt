package com.snowman.project.service.push

import com.google.firebase.messaging.*
import com.snowman.project.dao.push.PushRepository
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.push.dto.PushHistoryDto
import com.snowman.project.model.push.dto.SendPushMessageDto
import com.snowman.project.model.push.entity.PushMessage
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.push.exceptions.AlarmNotFoundException
import com.snowman.project.utils.push.PushUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PushService(
    val pushRepository: PushRepository
) {

    fun readAlarm(alarmId: Long): PushHistoryDto {
        val push = pushRepository.findByIdOrNull(alarmId) ?: throw AlarmNotFoundException()
        push.readAlarm()

        return PushHistoryDto(push)
    }

    @Throws(FirebaseMessagingException::class)
    fun sendPushMessages(userList: List<User>, type: PushType) {
        val tokenList = userList.map { it.fcmToken }.filterNotNull()
        var sendPushMessageDto: SendPushMessageDto? = null

        when (type) {
            PushType.WRITE -> {
                sendPushMessageDto = PushUtil.todoWriteAlarm()
            }
            PushType.CHEERUP -> {
                sendPushMessageDto = PushUtil.getCheerUpAlarm()
            }
        }
        sendPushMessageDto?.let {
            userList.forEach { user ->
                pushRepository.save(
                    PushMessage(
                        user = user,
                        title = it.title,
                        body = it.body
                    )
                )
            }
            sendIOS(tokenList, it)
        }
    }

    @Throws(FirebaseMessagingException::class)
    fun sendPushMessage(user: User, type: PushType, dto: SimpleGoalInfoDto) {
        val token = user.fcmToken
        token?.let {
            var sendPushMessageDto: SendPushMessageDto? = null
            when (type) {
                PushType.ALLCLEAR -> {
                    sendPushMessageDto = PushUtil.allClearAlarm(dto.objective)
                }
                PushType.LEVELUP -> {
                    sendPushMessageDto = PushUtil.levelUpAlarm(dto.name, dto.level)
                }
                PushType.WRITE -> {
                    sendPushMessageDto = PushUtil.todoWriteAlarm()
                }
            }
            sendPushMessageDto?.let {
                pushRepository.save(
                    PushMessage(
                        user = user,
                        title = it.title,
                        body = it.body
                    )
                )
                sendIOS(token, sendPushMessageDto)
            }
        }
    }

    private fun sendIOS(token: String, sendPushMessageDto: SendPushMessageDto) {
        val message = Message.builder()
            .setNotification(Notification(sendPushMessageDto.title, sendPushMessageDto.body))
            .putData("title", sendPushMessageDto.title)
            .setToken(token)
            .setApnsConfig(
                ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).build()
            ).build()
        FirebaseMessaging.getInstance().send(message)
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