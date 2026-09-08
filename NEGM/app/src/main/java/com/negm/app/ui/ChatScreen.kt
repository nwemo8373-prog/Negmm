package com.negm.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen(
    chat: DemoChat,
    onBack: () -> Unit
) {

    var text by remember {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf(
            "أهلاً بك في NEGM"
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            TextButton(
                onClick = onBack
            ) {
                Text("رجوع")
            }

            Text(
                text = chat.name,
                modifier = Modifier.padding(
                    start = 12.dp
                )
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            items(messages) { message ->

                Surface(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = NegmPanel2
                ) {

                    Text(
                        text = message,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("اكتب رسالة...")
                }
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            IconButton(
                onClick = {

                    if (text.isNotBlank()) {

                        messages.add(text)

                        text = ""
                    }
                }
            ) {

                Icon(
                    Icons.Default.Send,
                    contentDescription = "إرسال"
                )
            }
        }
    }
}
