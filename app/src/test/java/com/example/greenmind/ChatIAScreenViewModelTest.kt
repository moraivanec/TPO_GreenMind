package com.example.greenmind

import com.example.greenmind.components.chatia.ChatIAScreenViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

// Prueba que al enviar un mensaje se agregue primero la pregunta del usuario
// después la respuesta del asistente, que el input se limpie
// y que el estado de carga finalice correctamente

@OptIn(ExperimentalCoroutinesApi::class)
class ChatIAScreenViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al enviar mensaje agrega pregunta y respuesta`() = runTest {
        val repo = FakeGeminiRepository(
            answer = "Tenés que regar la planta cuando la tierra esté seca."
        )

        val vm = ChatIAScreenViewModel(repo)

        vm.onMessageChange("¿Cómo riego una suculenta?")
        vm.sendMessage()

        advanceUntilIdle()

        val state = vm.uiState.value

        assertFalse(state.isLoading)
        assertEquals("", state.inputMessage)

        val lastUserMessage = state.messages[state.messages.size - 2]
        val lastAssistantMessage = state.messages[state.messages.size - 1]

        assertEquals("¿Cómo riego una suculenta?", lastUserMessage.text)
        assertEquals(true, lastUserMessage.isFromUser)
        assertEquals("Tenés que regar la planta cuando la tierra esté seca.", lastAssistantMessage.text)
        assertEquals(false, lastAssistantMessage.isFromUser)
    }
}