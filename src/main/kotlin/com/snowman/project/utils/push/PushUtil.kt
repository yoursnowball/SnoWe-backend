package com.snowman.project.utils.push

import com.snowman.project.model.push.dto.PushDto

class PushUtil {

    companion object {
        fun getDeadLineAlarm(): PushDto {
            return PushDto(
                title = "오늘의 투투마감 임박",
                body = "서두르세요!\n오늘의 투두완료 시간이 얼마 남지 않았어요!"
            )
        }

        fun todoWriteAlarm(): PushDto {
            return PushDto(
                title = "내일의 투투작성 시간",
                body = "내일도 힘내볼까요? 응원하고있을게요"
            )
        }

        fun levelUpAlarm(name: String, level: Int): PushDto {
            return PushDto(
                title = "[$name] 레벨업!",
                body = "[$name]이 lv{$level}이 되었어요!"
            )
        }

        fun allClearAlarm(objective: String): PushDto {
            return PushDto(
                title = "[목표]완료",
                body = "[$objective] 투두를 모두 완료하셨군요! \n 너무 수고했어요"
            )
        }
    }
}