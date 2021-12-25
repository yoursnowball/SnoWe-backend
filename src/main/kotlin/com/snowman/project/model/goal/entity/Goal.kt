package com.snowman.project.model.goal.entity

import com.snowman.project.model.common.entity.BaseTimeEntity
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.common.event.Events
import com.snowman.project.service.goal.event.GoalLevelUpEvent
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import kotlin.math.pow

@Entity
@Table(name = "goals")
class Goal(
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

    fun todoChecked() {
        succeedTodoCount++
        levelTodoCount++
        if (isLevelUp()) levelUp()

    }

    fun todoUnchecked() {
        succeedTodoCount--
        if (isLevelDown())
            levelDown()
        else
            levelTodoCount--
    }

    private fun isLevelDown(): Boolean {
        return levelTodoCount == 0
    }

    private fun isLevelUp(): Boolean {
        return levelTodoCount >= (level.toDouble().pow(3)).toInt()
    }

    private fun levelUp() {
        levelTodoCount = 0
        level++
        Events.raise(GoalLevelUpEvent(this))
    }

    private fun levelDown() {
        level--
        levelTodoCount = (level.toDouble().pow(3).toInt()) - 1
    }

}
