package com.snowman.project.notifications.service

import com.snowman.project.common.exception.ApiException
import com.snowman.project.common.exception.ErrorCode
import com.snowman.project.notifications.domain.enums.PushType
import com.snowman.project.users.dao.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class PushMessageService(
    val userRepository: UserRepository,
    val pushService: PushService
) {

    fun send(type: PushType) {
        when (type) {
            PushType.CHEERUP -> sendNotWriteTodoYetReceiver()
            PushType.WRITE -> sendNotCompleteAllTodoYetReceiver()
            PushType.DAILY -> sendAllDailyAlarm()
            else -> throw ApiException(ErrorCode.ALARM_NOT_FOUND);
        }
    }

    /**
     * 23시에 내일의 투두를 작성하지 않은 사람에게만 알람
     */
    private fun sendNotWriteTodoYetReceiver() {
        val tomorrow = LocalDate.now().plusDays(1)
        val userList = userRepository.findUserNotWriteTomorrowTodoYet(tomorrow)
        pushService.sendPushMessages(userList, PushType.WRITE)
    }

    /**
     * 오늘의 투두를 아직 다 완료하지 못한사람에게 14시에 알람
     */
    private fun sendNotCompleteAllTodoYetReceiver() {
        val userList = userRepository.findUserNotCompleteTodayTodoYet()
        pushService.sendPushMessages(userList, PushType.CHEERUP)
    }

    /**
     * 아침 9시에 보내주는 격려 알람
     */
    private fun sendAllDailyAlarm() {
        val userList = userRepository.findAllByFcmTokenIsNotNull()
        pushService.sendPushMessages(userList, PushType.DAILY)
    }
}