package com.snowman.project.model.goal.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.goal.enums.LevelChange
import com.snowman.project.model.user.entity.User
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.math.pow

@Entity
@Table(name = "goals")
data class Goal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", length = 20, nullable = false)
    val name: String,

    @Column(name = "objective", nullable = false)
    val objective: String,

    @Column(name = "succeed_todo_count")
    var succeedTodoCount: Int = 0,

    @Column(name = "level_todo_count")
    var levelTodoCount: Int = 0,

    @Column(name = "level")
    var level: Int = 1,

    @Column(name = "is_deleted")
    var deleted: Boolean = false,

    @Column(name = "is_award")
    var awarded: Boolean = false,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    val characterType: CharacterType,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) : BaseTimeEntity() {

    fun canMoveToAwards(): Boolean {
        return level >= 5;
    }

    fun delete() {
        this.deleted = true
        this.finishedAt = LocalDateTime.now()
    }

    fun moveToAward() {
        this.awarded = true
        this.finishedAt = LocalDateTime.now()
    }

    fun todoChange(flag: Boolean): LevelChange {
        if (flag) {
            succeedTodoCount++
            levelTodoCount++

            if (isLevelUp()) {
                levelTodoCount = 0
                level++
                return LevelChange.LEVELUP
            }
        } else {
            succeedTodoCount--
            if (isLevelDown()) {
                level--
                levelTodoCount = (level.toDouble().pow(3).toInt()) - 1
                return LevelChange.LEVELDOWN
            } else {
                levelTodoCount--
            }
        }
        return LevelChange.KEEP
    }

    private fun isLevelDown(): Boolean {
        return levelTodoCount == 0
    }

    private fun isLevelUp(): Boolean {
        return levelTodoCount >= (level.toDouble().pow(3)).toInt()
    }

}
