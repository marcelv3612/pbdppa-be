package com.bdcga.pbdpp.exception// package com.bdcga.pbdpp.exception

import com.bdcga.pbdpp.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalExceptionHandler {

    fun handleValidationExceptions(ex: WebExchangeBindException): ResponseEntity<Any> {
        val errors = ex.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity.badRequest().body(errors)
    }

    fun handleAllExceptions(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse(false, "An unexpected error occurred"))
    }
}