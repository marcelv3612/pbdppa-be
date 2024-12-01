package com.bdcga.pbdpp.service// package com.bdcga.pbdpp.service

import com.bdcga.pbdpp.dto.ApiResponse
import com.bdcga.pbdpp.dto.TransactionData
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SolanaService(
    @Value("\${solana.module.base-url}")
    private val rustModuleBaseUrl: String,

    private val webClientBuilder: WebClient.Builder
) {

    private val webClient = webClientBuilder.baseUrl(rustModuleBaseUrl).build()

    suspend fun sendMemo(transactionData: TransactionData): ResponseEntity<Any> {
        return try {
            val response = webClient.post()
                .uri("/sendMemo")
                .bodyValue(transactionData)
                .retrieve()
                .bodyToMono(ApiResponse::class.java)
                .awaitSingle()
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse(false, "Blockchain service issue"))
        }
    }

    suspend fun fetchMemo(transactionId: String): ResponseEntity<Any> {
        return try {
            val response = webClient.get()
                .uri("/fetchMemo/{transactionId}", transactionId)
                .retrieve()
                .bodyToMono(String::class.java)
                .awaitSingle()
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse(false, "Blockchain retrieval issue"))
        }
    }

    suspend fun validateData(transactionData: TransactionData): ResponseEntity<Any> {
        return try {
            val response = webClient.post()
                .uri("/validateData")
                .bodyValue(transactionData)
                .retrieve()
                .bodyToMono(ApiResponse::class.java)
                .awaitSingle()
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse(false, "Blockchain service issue"))
        }
    }
}