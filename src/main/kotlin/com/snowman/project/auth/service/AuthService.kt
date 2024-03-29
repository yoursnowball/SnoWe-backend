package com.snowman.project.auth.service

import com.snowman.project.security.JwtTokenProvider
import com.snowman.project.users.dao.UserRepository
import com.snowman.project.users.model.entity.User
import com.snowman.project.auth.service.exceptions.DuplicateNickNameException
import com.snowman.project.auth.service.exceptions.DuplicateUserNameException
import com.snowman.project.auth.service.exceptions.IncorrectUserInfoException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuthService(
    val passwordEncoder: PasswordEncoder,
    val jwtTokenProvider: JwtTokenProvider,
    val userRepository: UserRepository
) {

    fun signUp(userName: String, password: String, nickName: String): String {
        if (userRepository.existsByUserName(userName))
            throw DuplicateUserNameException()
        if (userRepository.existsByNickName(nickName))
            throw DuplicateNickNameException()

        val user = userRepository.save(
            User(
                id = null,
                userName = userName,
                password = passwordEncoder.encode(password),
                nickName = nickName
            )
        )
        return jwtTokenProvider.generateToken(user.id!!, user.userName)
    }

    fun signIn(userName: String, password: String): String {
        val user =
            userRepository.findByUserName(userName) ?: throw IncorrectUserInfoException()

        if (!passwordEncoder.matches(password, user.password))
            throw IncorrectUserInfoException()
        return jwtTokenProvider.generateToken(user.id!!, user.userName)
    }
}