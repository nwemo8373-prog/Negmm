package com.negm.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class DemoChat(
    val id: String,
    val name: String,
    val message: String
)

@Composable
fun HomeScreen(
    onOpenChat: (DemoChat) -> Unit
) {

    var search by remember {
        mutableStateOf("")
    }

    val chats = remember {

        listOf(

            DemoChat(
                "1",
                "أحمد",
                "أهلاً بك في NEGM"
            ),

            DemoChat(
                "2",
                "محمد",
                "فينك؟"
            ),

            DemoChat(
                "3",
                "NEGM Team",
                "مرحباً بك"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "NEGM",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Row {

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Calls"
                    )
                }

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.VideoCall,
                        contentDescription = "Video"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("بحث...")
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "القصص",
            color = NegmMuted
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row {

            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(NegmPanel2)
                    .clickable {},
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription = "إضافة قصة",
                    tint = NegmAccent
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "قصتي",
                modifier = Modifier.align(
                    Alignment.CenterVertically
                )
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "المحادثات",
            color = NegmMuted
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LazyColumn {

            items(
                chats.filter {
                    it.name.contains(
                        search,
                        ignoreCase = true
                    )
                }
            ) { chat ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .background(
                            NegmPanel,
                            MaterialTheme.shapes.large
                        )
                        .clickable {
                            onOpenChat(chat)
                        }
                        .padding(14.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(NegmAccent),
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            chat.name
                        )

                        Text(
                            chat.message,
                            color = NegmMuted
                        )
                    }
                }
            }
        }
    }
}
