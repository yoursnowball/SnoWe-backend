package com.snowman.project.goals.domain.entity

import com.snowman.project.common.domain.entity.AbstractAggregateEntity
import com.snowman.project.goals.domain.enums.CharacterType
import com.snowman.project.goals.domain.event.GoalLevelUpEvent
import com.snowman.project.users.model.entity.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
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
@EntityListeners(AuditingEntityListener::class)
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
    val user: User,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null
) : AbstractAggregateEntity() {

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

    fun todoSuccess() {
        succeedTodoCount++
        levelTodoCount++

        if (isLevelUp()) {
            levelTodoCount = 0
            level++
            registerEvent(GoalLevelUpEvent(user.id!!, id!!))
        }
    }

    fun todoFail() {
        succeedTodoCount--
        if (isLevelDown()) {
            level--
            levelTodoCount = (level.toDouble().pow(3).toInt()) - 1
        } else {
            levelTodoCount--
        }
    }


    private fun isLevelDown(): Boolean {
        return levelTodoCount == 0
    }

    private fun isLevelUp(): Boolean {
        return levelTodoCount >= (level.toDouble().pow(3)).toInt()
    }

}
