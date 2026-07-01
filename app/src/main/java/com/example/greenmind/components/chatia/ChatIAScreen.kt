package com.example.greenmind.components.chatia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.GreenMindBottomBar
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindInputBorder
import com.example.greenmind.ui.theme.GreenMindInputGray
import com.example.greenmind.ui.theme.GreenMindOnline
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindSearchSoft
import com.example.greenmind.ui.theme.GreenMindSoft
import com.example.greenmind.ui.theme.GreenMindTextDark
import com.example.greenmind.ui.theme.GreenMindTextGray
import com.example.greenmind.ui.theme.GreenMindTextGreenDark
import com.example.greenmind.ui.theme.GreenMindTextMuted
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun ChatIAScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: ChatIAScreenViewModel = hiltViewModel()
) {
    // Observa el estado del ViewModel respetando el ciclo de vida de la pantalla
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
    ) {
        ChatIAHeader()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Muestra todos los mensajes del chat, tanto del usuario como del asistente
            uiState.messages.forEach { message ->
                ChatBubble(message = message)

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Mientras Gemini genera una respuesta, se muestra un mensaje temporal
            if (uiState.isLoading) {
                ChatBubble(
                    message = ChatMessage(
                        text = "Pensando una respuesta...",
                        isFromUser = false
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Preguntas sugeridas:",
                style = MaterialTheme.typography.bodyMedium,
                color = GreenMindTextMuted,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Preguntas pre definidas para que el usuario pueda consultar sin escribir
            Row {
                SuggestedQuestionButton(
                    text = uiState.suggestedQuestions[0],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vm.onSuggestedQuestionClick(uiState.suggestedQuestions[0])
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                SuggestedQuestionButton(
                    text = uiState.suggestedQuestions[1],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vm.onSuggestedQuestionClick(uiState.suggestedQuestions[1])
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                SuggestedQuestionButton(
                    text = uiState.suggestedQuestions[2],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vm.onSuggestedQuestionClick(uiState.suggestedQuestions[2])
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                SuggestedQuestionButton(
                    text = uiState.suggestedQuestions[3],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vm.onSuggestedQuestionClick(uiState.suggestedQuestions[3])
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = uiState.inputMessage,
                    onValueChange = {
                        vm.onMessageChange(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    placeholder = {
                        Text(
                            text = "Escribí tu pregunta...",
                            color = GreenMindTextMuted
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = GreenMindInputGray,
                        unfocusedContainerColor = GreenMindInputGray,
                        focusedBorderColor = GreenMindInputBorder,
                        unfocusedBorderColor = GreenMindInputBorder,
                        focusedTextColor = GreenMindTextDark,
                        unfocusedTextColor = GreenMindTextDark,
                        cursorColor = GreenMindPrimary
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Botón de enviar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GreenMindInputGray)
                        .clickable {
                            vm.sendMessage()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "➤",
                        color = GreenMindTextGray,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        // Barra inferior para navegar entre las secciones principales
        GreenMindBottomBar(
            selectedRoute = Screen.ChatIA.route,
            onHomeClick = {
                navController.navigate(Screen.PlantList.route) {
                    launchSingleTop = true
                }
            },
            onSearchClick = {
                navController.navigate(Screen.Buscar.route) {
                    launchSingleTop = true
                }
            },
            onMiJardinClick = {
                navController.navigate(Screen.MiJardin.route) {
                    launchSingleTop = true
                }
            },
            onChatClick = {
                navController.navigate(Screen.ChatIA.route) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun ChatIAHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = GreenMindPrimary,
                shape = RoundedCornerShape(
                    bottomStart = 34.dp,
                    bottomEnd = 34.dp
                )
            )
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(GreenMindSoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🗨",
                    style = MaterialTheme.typography.headlineSmall,
                    color = GreenMindPrimary
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Chat de Jardinería",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = GreenMindWhite
                )

                Text(
                    text = "● Asistente en línea",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreenMindOnline
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage
) {
    val backgroundColor = if (message.isFromUser) GreenMindSearchSoft else GreenMindWhite
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .padding(14.dp)
        ) {
            if (!message.isFromUser) {
                Text(
                    text = "✧ Asistente IA",
                    style = MaterialTheme.typography.bodySmall,
                    color = GreenMindTextMuted,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = GreenMindTextDark
            )
        }
    }
}

@Composable
fun SuggestedQuestionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(GreenMindSoft)
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = GreenMindTextGreenDark,
            fontWeight = FontWeight.Medium
        )
    }
}