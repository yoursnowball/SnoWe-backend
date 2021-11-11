package com.snowman.project.service.user

import com.snowman.project.dao.user.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService(val userRepository: UserRepository) {
}