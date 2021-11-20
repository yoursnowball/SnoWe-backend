package com.snowman.project.service.push

import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.push.enums.PushType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class PushScheduler(
    val userRepository: UserRepository,
    val pushService: PushService
) {
    /**
     * 23시에 내일의 투두를 작성하지 않은 사람에게만 알람
     */
    @Scheduled(cron = "0 0 23 * * *")
    fun findNotWriteTodoYetReceiver() {
        val tomorrow = LocalDate.now().plusDays(1)
        val userList = userRepository.findUserNotWriteTomorrowTodoYet(tomorrow)
        pushService.sendPushMessages(userList, PushType.WRITE)
    }

    /**
     * 오늘의 투두를 아직 다 완료하지 못한사람에게 14시에 알람
     */
    @Scheduled(cron = "0 0 14 * * *")
    fun findNotCompleteAllTodoYetReceiver() {
        val userList = userRepository.findUserNotCompleteTodayTodoYet()
        pushService.sendPushMessages(userList, PushType.CHEERUP)
    }
}