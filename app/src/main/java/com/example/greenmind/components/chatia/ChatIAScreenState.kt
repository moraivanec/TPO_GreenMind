package com.example.greenmind.components.chatia

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean // Permite diferenciar si el mensaje lo envió el usuario o la IA
)

data class ChatIAScreenState(
    val inputMessage: String = "",
    val messages: List<ChatMessage> = listOf(
        ChatMessage(
            text = "¡Hola! Soy tu asistente de jardinería. Puedo ayudarte con consejos sobre el cuidado de las plantas, resolver dudas y darte recomendaciones personalizadas. ¿En qué puedo ayudarte hoy?",
            isFromUser = false
        )
    ),
    val suggestedQuestions: List<String> = listOf(
        "¿Cómo riego mis plantas?",
        "¿Qué plantas son fáciles para principiantes?",
        "¿Cuánta luz necesitan?",
        "¿Por qué las hojas se ponen amarillas?"
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)