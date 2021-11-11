package com.snowman.project.dao.user

import com.snowman.project.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
}