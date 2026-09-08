package com.negm.app.ui

import androidx.compose.runtime.*

@Composable
fun NegmApp() {

    var loggedIn by remember {
        mutableStateOf(false)
    }

    var selectedChat by remember {
        mutableStateOf<DemoChat?>(null)
    }

    when {

        !loggedIn -> {

            AuthScreen(
                onSuccess = {
                    loggedIn = true
                }
            )
        }

        selectedChat != null -> {

            ChatScreen(
                chat = selectedChat!!,
                onBack = {
                    selectedChat = null
                }
            )
        }

        else -> {

            HomeScreen(
                onOpenChat = {
                    selectedChat = it
                }
            )
        }
    }
}
