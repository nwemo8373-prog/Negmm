package com.negm.app.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.negm.app.R

@Composable
fun AuthScreen(
    onSuccess: () -> Unit
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.negm_logo
            ),
            contentDescription = "NEGM",
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "NEGM",
            fontSize = 34.sp
        )

        Text(
            text = "Messenger",
            color = NegmMuted
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("البريد الإلكتروني")
            }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("كلمة المرور")
            },
            visualTransformation =
                PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !loading,
            onClick = {

                if (
                    email.isBlank() ||
                    password.isBlank()
                ) {

                    Toast.makeText(
                        context,
                        "أكمل البيانات المطلوبة",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                loading = true

                FirebaseAuth
                    .getInstance()
                    .signInWithEmailAndPassword(
                        email.trim(),
                        password
                    )
                    .addOnCompleteListener {

                        loading = false

                        if (it.isSuccessful) {

                            onSuccess()

                        } else {

                            Toast.makeText(
                                context,
                                it.exception?.message
                                    ?: "فشل تسجيل الدخول",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        ) {

            Text(
                if (loading)
                    "جارٍ الدخول..."
                else
                    "دخول"
            )
        }
    }
}
