package com.snowman.project.service

import com.snowman.project.dao.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuthService(val userRepository: UserRepository) {
}