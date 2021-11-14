package com.snowman.project.model.goal.entity

import com.snowman.project.model.common.BaseTimeEntity
import com.snowman.project.model.goal.enums.CharacterType
import com.snowman.project.model.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "goals")
data class Goal(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "name", length = 20, nullable = false)
    val name: String,

    @Column(name = "succeed_todo_count")
    var succeedTodoCount: Int = 0,

    @Column(name = "level")
    var level: Int = 0,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Enumerated(EnumType.STRING)
    val characterType: CharacterType,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) : BaseTimeEntity() {

    fun delete() {
        this.isDeleted = true
    }
}
