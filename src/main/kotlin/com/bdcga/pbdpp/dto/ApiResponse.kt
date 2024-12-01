// package com.bdcga.pbdpp.dto

package com.bdcga.pbdpp.dto

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)