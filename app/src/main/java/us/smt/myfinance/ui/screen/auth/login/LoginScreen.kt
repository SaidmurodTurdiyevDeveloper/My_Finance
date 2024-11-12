package us.smt.myfinance.ui.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()
        LoginPage(
            state = state,
            onAction = viewModel::onAction
        )
    }

}

@Composable
fun LoginPage(
    state: LoginState,
    onAction: (LoginIntent) -> Unit
) {
    // State variables for email and password fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Main layout for the login screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title text
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Password TextField
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Login button
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context, "Shahzoda yomon qizcha", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Login", fontSize = 18.sp)
            }

            // Register text
            TextButton(
                onClick = { /* Navigate to registration screen */ },
            ) {
                Text("Don't have an account? Register")
            }
        }
    }

}

@Preview
@Composable
private fun LoginPagePrev() {
    LoginPage(LoginState()) {}
}