package com.example.greenmind.data

import com.example.greenmind.domain.IGeminiRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class GeminiRepository : IGeminiRepository {

    private val model = Firebase
        .ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-3.5-flash")

    override suspend fun askGardeningQuestion(question: String): String {
        val prompt = """
            Sos un asistente de jardinería dentro de una app llamada GreenMind.
            
            Respondé siempre en español.
            Respondé de forma clara, simple y útil.
            Ayudá especialmente a personas principiantes con el cuidado de plantas.
            
            Solo podés responder preguntas relacionadas con:
            - plantas
            - jardinería
            - riego
            - luz solar
            - macetas
            - sustrato
            - hojas
            - plagas
            - cuidados generales
            
            Si la pregunta no está relacionada con jardinería o plantas, respondé amablemente:
            "Solo puedo ayudarte con temas relacionados con plantas y jardinería."
            
            Pregunta del usuario:
            $question
        """.trimIndent()

        val response = model.generateContent(prompt)

        return response.text ?: "No pude generar una respuesta en este momento."
    }
}