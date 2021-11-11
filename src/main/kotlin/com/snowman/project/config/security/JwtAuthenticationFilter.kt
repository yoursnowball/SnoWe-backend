package com.snowman.project.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.snowman.project.config.exceptions.ErrorCode
import com.snowman.project.config.exceptions.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response);
        } catch (e: SignatureException) {

        } catch (e: MalformedJwtException) {

        } catch (e: ExpiredJwtException) {

        } catch (e: DecodingException) {

        }
    }

    @Throws(IOException::class)
    private fun sendErrorMessage(res: HttpServletResponse, code: ErrorCode) {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter()
            .write(objectMapper.writeValueAsString(ErrorResponse(code.code, code.message)));
    }
}