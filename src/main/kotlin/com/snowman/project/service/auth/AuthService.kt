package com.snowman.project.service.auth

import com.snowman.project.dao.user.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuthService(val userRepository: UserRepository) {
}