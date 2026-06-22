package com.example.greenmind.components.chatia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.data.GeminiRepository
import com.example.greenmind.domain.IGeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatIAScreenViewModel(
    private val geminiRepository: IGeminiRepository = GeminiRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatIAScreenState())
    val uiState: StateFlow<ChatIAScreenState> = _uiState.asStateFlow()

    fun onMessageChange(value: String) {
        _uiState.update {
            it.copy(inputMessage = value)
        }
    }

    fun onSuggestedQuestionClick(question: String) {
        sendQuestion(question)
    }

    fun sendMessage() {
        sendQuestion(_uiState.value.inputMessage)
    }

    private fun sendQuestion(rawQuestion: String) {
        val question = rawQuestion.trim()

        if (question.isBlank()) {
            return
        }

        val userMessage = ChatMessage(
            text = question,
            isFromUser = true
        )

        _uiState.update {
            it.copy(
                inputMessage = "",
                messages = it.messages + userMessage,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val answer = geminiRepository.askGardeningQuestion(question)

                val assistantMessage = ChatMessage(
                    text = answer,
                    isFromUser = false
                )

                _uiState.update {
                    it.copy(
                        messages = it.messages + assistantMessage,
                        isLoading = false,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                val assistantMessage = ChatMessage(
                    text = "No se puede conectar con el asistente en este momento. Revisá tu conexión e intentá nuevamente.",
                    isFromUser = false
                )

                _uiState.update {
                    it.copy(
                        messages = it.messages + assistantMessage,
                        isLoading = false,
                        errorMessage = e.localizedMessage
                    )
                }
            }
        }
    }
}