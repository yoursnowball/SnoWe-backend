package com.snowman.project.service.auth

import com.snowman.project.config.security.JwtTokenProvider
import com.snowman.project.dao.user.UserRepository
import com.snowman.project.model.user.entity.User
import com.snowman.project.service.auth.exceptions.DuplicateNickNameException
import com.snowman.project.service.auth.exceptions.DuplicateUserNameException
import com.snowman.project.service.auth.exceptions.IncorrectUserInfoException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AuthService(
    val passwordEncoder: PasswordEncoder,
    val jwtTokenProvider: JwtTokenProvider,
    val userRepository: UserRepository
) {

    @Transactional
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