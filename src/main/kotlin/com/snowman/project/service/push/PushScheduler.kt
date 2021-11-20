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
     * 12~23시에
     */
    @Scheduled(cron = "0 0 23 * * *")
    fun findNotWriteTodoYetReceiver() {
        val tomorrow = LocalDate.now().plusDays(1)
        val userList = userRepository.findUserNotWriteTodoYet(tomorrow)
        pushService.sendPushMessages(userList, PushType.WRITE)
    }
}