package com.example.greenmind.domain

interface IGeminiRepository {

    suspend fun askGardeningQuestion(question: String): String
}