package com.snowman.project.common.utils.push

import com.snowman.project.notifications.domain.dto.SendPushMessageDto

class PushUtil {

    companion object {
        fun getCheerUpAlarm(): SendPushMessageDto {
            return SendPushMessageDto(
                title = "오늘의 투투를 완료해보세요!",
                body = "힘내세요!\n오늘의 투두완료를 위해 열심히 달려봐요!" + String(Character.toChars(0x1F4AA))
            )
        }

        fun todoWriteAlarm(): SendPushMessageDto {
            return SendPushMessageDto(
                title = "내일의 투투작성 시간" + String(Character.toChars(0x1F4DD)),
                body = "내일도 힘내볼까요? 응원하고있을게요" + String(Character.toChars(0x2603))
            )
        }

        fun levelUpAlarm(name: String, level: Int): SendPushMessageDto {
            return SendPushMessageDto(
                title = "[$name] 레벨업!",
                body = "[$name]이 lv{$level}이 되었어요!" + String(Character.toChars(0x1F389))
            )
        }

        fun allClearAlarm(objective: String): SendPushMessageDto {
            return SendPushMessageDto(
                title = "[목표]완료",
                body = "[$objective] 투두를 모두 완료하셨군요! \n 너무 수고했어요" + String(Character.toChars(0x1F44F))
            )
        }

        fun dailyMorningAlarm(): SendPushMessageDto {
            return SendPushMessageDto(
                title = "좋은 아침이에요!",
                body = "오늘도 활기찬 하루를 보내봐요!" + String(Character.toChars(0x2600))
            )
        }
    }
}