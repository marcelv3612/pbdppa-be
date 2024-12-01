package com.bdcga.pbdpp.controller// package com.bdcga.pbdpp.controller

import com.bdcga.pbdpp.dto.TransactionData
import com.bdcga.pbdpp.service.SolanaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/solana")
class SolanaController(
    private val solanaService: SolanaService
) {

    @PostMapping("/sendMemo")
    suspend fun sendMemo(@RequestBody transactionData: TransactionData): ResponseEntity<Any> {
        return solanaService.sendMemo(transactionData)
    }

    @GetMapping("/fetchMemo/{transactionId}")
    suspend fun fetchMemo(@PathVariable transactionId: String): ResponseEntity<Any> {
        return solanaService.fetchMemo(transactionId)
    }

    @PostMapping("/validateData")
    suspend fun validateData(@RequestBody transactionData: TransactionData): ResponseEntity<Any> {
        return solanaService.validateData(transactionData)
    }
}