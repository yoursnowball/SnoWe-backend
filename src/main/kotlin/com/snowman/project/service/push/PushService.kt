package com.snowman.project.service.push

import com.google.firebase.messaging.*
import com.snowman.project.model.goal.dto.SimpleGoalInfoDto
import com.snowman.project.model.push.dto.PushDto
import com.snowman.project.model.push.enums.PushType
import com.snowman.project.model.user.entity.User
import com.snowman.project.utils.push.PushUtil
import org.springframework.stereotype.Service

@Service
class PushService {
    @Throws(FirebaseMessagingException::class)
    fun sendPushMessages(userList: List<User>, type: PushType) {
        val tokenList = userList.map { it.fcmToken!! }
        var pushDto: PushDto? = null

        when (type) {
            PushType.WRITE -> {
                pushDto = PushUtil.todoWriteAlarm()
            }
            PushType.DEADLINE -> {
                pushDto = PushUtil.getDeadLineAlarm()
            }
        }
        pushDto?.let { sendIOS(tokenList, it) }
    }

    @Throws(FirebaseMessagingException::class)
    fun sendPushMessage(user: User, type: PushType, dto: SimpleGoalInfoDto) {
        val token = user.fcmToken!!
        var pushDto: PushDto? = null
        when (type) {
            PushType.ALLCLEAR -> {
                pushDto = PushUtil.allClearAlarm(dto.objective)
            }
            PushType.LEVELUP -> {
                pushDto = PushUtil.levelUpAlarm(dto.name, dto.level)
            }
            PushType.WRITE -> {
                pushDto = PushUtil.todoWriteAlarm()
            }
        }
        pushDto?.let { sendIOS(token, pushDto) }
    }

    private fun sendIOS(token: String, pushDto: PushDto) {
        val message = Message.builder()
            .setNotification(Notification(pushDto.title, pushDto.body))
            .putData("title", pushDto.title)
            .setToken(token)
            .setApnsConfig(
                ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).build()
            ).build()
        FirebaseMessaging.getInstance().send(message)
    }

    private fun sendIOS(tokenList: List<String>, pushDto: PushDto): Int {
        return if (tokenList.isNotEmpty()) {
            val multicast = MulticastMessage.builder()
                .setNotification(Notification(pushDto.title, pushDto.body))
                .putData("title", pushDto.title)
                .addAllTokens(tokenList)
                .setApnsConfig(
                    ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).build()
                ).build()
            FirebaseMessaging.getInstance().sendMulticast(multicast).successCount
        } else
            0
    }
}