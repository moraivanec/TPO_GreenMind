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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.GreenMindBottomBar

@Composable
fun ChatIAScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: ChatIAScreenViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F1))
    ) {
        ChatIAHeader()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            uiState.messages.forEach { message ->
                ChatBubble(message = message)

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Preguntas sugeridas:",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9A9A9A),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                            text = "Escribe tu pregunta...",
                            color = Color(0xFF9A9A9A)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE3E6E0),
                        unfocusedContainerColor = Color(0xFFE3E6E0),
                        focusedBorderColor = Color(0xFFD0D5CC),
                        unfocusedBorderColor = Color(0xFFD0D5CC),
                        focusedTextColor = Color(0xFF222222),
                        unfocusedTextColor = Color(0xFF222222),
                        cursorColor = Color(0xFF557B45)
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3E6E0))
                        .clickable {
                            vm.sendMessage()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "➤",
                        color = Color(0xFF8A8A8A),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

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
                color = Color(0xFF557B45),
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
                    .background(Color(0xFFDDE8D8)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🗨",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF557B45)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Chat de Jardinería",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "● Asistente en línea",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF9EE493)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage
) {
    val backgroundColor = if (message.isFromUser) Color(0xFFD9EBD3) else Color.White
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
                    color = Color(0xFF9A9A9A),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF222222)
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
            .background(Color(0xFFDDE8D8))
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF3F6138),
            fontWeight = FontWeight.Medium
        )
    }
}