package com.snowman.project.dao.user

import com.snowman.project.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
    fun existsByUserName(userName: String): Boolean
    fun existsByNickName(nickName: String): Boolean
    fun findByUserName(userName: String): User?
}