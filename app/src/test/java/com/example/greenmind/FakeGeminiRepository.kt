package com.example.greenmind

import com.example.greenmind.domain.IGeminiRepository

class FakeGeminiRepository(
    private val answer: String = "Respuesta fake de Gemini",
    private val throwError: Boolean = false
) : IGeminiRepository {

    override suspend fun askGardeningQuestion(question: String): String {
        if (throwError) throw Exception("Error simulado")

        return answer
    }
}