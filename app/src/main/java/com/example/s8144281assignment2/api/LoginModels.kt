package com.example.s8144281assignment2.api

// What we SEND:  {"username": "...", "password": "..."}
data class LoginRequest(
    val username: String,
    val password: String
)

// What we GET BACK:  {"keypass": "topicName"}
data class LoginResponse(
    val keypass: String?
)