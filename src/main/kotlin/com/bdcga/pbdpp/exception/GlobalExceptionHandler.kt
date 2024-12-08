package com.bdcga.pbdpp.exception// package com.bdcga.pbdpp.exception

import com.bdcga.pbdpp.dto.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = ex.bindingResult.fieldErrors.map { fieldError ->
            mapOf(
                "field" to fieldError.field,
                "message" to fieldError.defaultMessage
            )
        }
        logger.error("Validation exception occurred", ex)
        return ResponseEntity.badRequest().body(mapOf("errors" to errors))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        logger.error("Unhandled exception occurred", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.")
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleValidationException(ex: ServerWebInputException): ResponseEntity<Any> {
        logger.error("Validation exception occurred", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.")
    }
}