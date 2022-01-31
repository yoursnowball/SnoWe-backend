package com.snowman.project.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.snowman.project.common.exception.ErrorCode
import com.snowman.project.common.exception.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
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
            jwtTokenProvider.resolveToken(request)
                ?.let { SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(it) }
            filterChain.doFilter(request, response);
        } catch (e: SignatureException) {
            sendErrorMessage(response, ErrorCode.INVALID_TOKEN)
        } catch (e: MalformedJwtException) {
            sendErrorMessage(response, ErrorCode.MALFORMED_TOKEN)
        } catch (e: ExpiredJwtException) {
            sendErrorMessage(response, ErrorCode.EXPIRE_TOKEN)
        } catch (e: DecodingException) {
            sendErrorMessage(response, ErrorCode.NOT_BEARER_FORMAT)
        }
    }

    @Throws(IOException::class)
    private fun sendErrorMessage(res: HttpServletResponse, code: ErrorCode) {
        res.status = HttpServletResponse.SC_FORBIDDEN;
        res.contentType = MediaType.APPLICATION_JSON_VALUE;
        res.writer.write(objectMapper.writeValueAsString(ErrorResponse(code.code, code.message, HttpStatus.FORBIDDEN.value())));
    }
}