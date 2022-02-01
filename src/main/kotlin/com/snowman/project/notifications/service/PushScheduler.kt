package com.snowman.project.notifications.service

import com.snowman.project.users.dao.UserRepository
import com.snowman.project.notifications.domain.enums.PushType
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
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
    @SchedulerLock(name = "SchedulerLock", lockAtLeastFor = "PT30S", lockAtMostFor = "PT30S")
    fun sendNotWriteTodoYetReceiver() {
        val tomorrow = LocalDate.now().plusDays(1)
        val userList = userRepository.findUserNotWriteTomorrowTodoYet(tomorrow)
        pushService.sendPushMessages(userList, PushType.WRITE)
    }

    /**
     * 오늘의 투두를 아직 다 완료하지 못한사람에게 14시에 알람
     */
    @Scheduled(cron = "0 0 14 * * *")
    @SchedulerLock(name = "SchedulerLock", lockAtLeastFor = "PT30S", lockAtMostFor = "PT30S")
    fun sendNotCompleteAllTodoYetReceiver() {
        val userList = userRepository.findUserNotCompleteTodayTodoYet()
        pushService.sendPushMessages(userList, PushType.CHEERUP)
    }

    /**
     * 아침 9시에 보내주는 격려 알람
     */
    @Scheduled(cron = "0 0 9 * * *")
    @SchedulerLock(name = "SchedulerLock", lockAtLeastFor = "PT30S", lockAtMostFor = "PT30S")
    fun sendAllDailyAlarm() {
        val userList = userRepository.findAllByFcmTokenIsNotNull()
        pushService.sendPushMessages(userList, PushType.DAILY)
    }
}