package us.smt.myfinance.ui.screen.sms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import us.smt.myfinance.ui.utils.TextFieldData

class SmsConfirmScreen(
    private val confirmFunction: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        OtpVerificationScreen(onResend = {}, onSubmit = {
            confirmFunction()
        }, timer = 30
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    onSubmit: (String) -> Unit, onResend: () -> Unit, timer: Int
) {
    var otp by remember { mutableStateOf(TextFieldData()) }

    val navigator = LocalNavigator.current

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator?.pop()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = { Text(text = "About", style = MaterialTheme.typography.headlineMedium) },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .background(Color(0xFFF5F5F5)), // Light gray background
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enter OTP", style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp), modifier = Modifier.padding(bottom = 16.dp)
            )

            // OTP Fields (4 input fields for each digit)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                InputOtpTextView(
                    inputValue = otp,
                    onTextChangeListener = {
                        otp = TextFieldData(text = it)
                    },
                    confirm = {},
                    isAutoOpenKeyboard = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            // Submit Button
            Button(
                onClick = {
                    if (otp.text.length == 4) {
                        onSubmit(otp.text)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Fixed height for better visuals
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)) // Blue button
            ) {
                Text("Verify OTP", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend OTP Button
            TextButton(
                onClick = {
                    onResend() // Trigger resend OTP action
                }, enabled = timer == 0, colors = ButtonDefaults.textButtonColors(contentColor = if (timer == 0) Color(0xFF1E88E5) else Color.Gray)
            ) {
                Text("Resend OTP" + if (timer == 0) "" else " ($timer sec)", fontSize = 16.sp)
            }
        }
    }
}


@Composable
private fun InputOtpTextView(
    modifier: Modifier = Modifier, inputValue: TextFieldData, maxCount: Int = 4, isAutoOpenKeyboard: Boolean = true, confirm: (text: String) -> Unit = {}, onTextChangeListener: (text: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (isAutoOpenKeyboard) {
        LaunchedEffect(true) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    BasicTextField(value = inputValue.text, modifier = modifier
        .focusRequester(focusRequester)
        .onFocusChanged {
            if (it.isFocused) {
                keyboardController?.show()
            }
        }
        .fillMaxWidth(), onValueChange = { text ->
        if (text.length <= maxCount) {
            onTextChangeListener(text)
        }
        if (text.length == maxCount) {
            focusManager.clearFocus()
        }
    }, keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done
    ), keyboardActions = KeyboardActions(onDone = {
        confirm(inputValue.text)
    }), decorationBox = {
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(maxCount) { index ->
                val char = when {
                    index >= inputValue.text.length -> ""
                    else -> inputValue.text[index].toString()
                }
                val isFocus = inputValue.text.length == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .width(if (maxCount == 6) 50.dp else 56.dp)
                        .height(64.dp)
                        .background(
                            shape = RoundedCornerShape(16.dp), color = Color.White
                        )
                        .border(
                            width = 1.dp, color = if (inputValue.error != null) Color.Red else if (isFocus) Color.Blue else Color.Gray, shape = RoundedCornerShape(16.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char, fontSize = 24.sp, color = Color.Black, textAlign = TextAlign.Center
                    )
                }

//                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    })
}
