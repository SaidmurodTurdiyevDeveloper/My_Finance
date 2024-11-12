package us.smt.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import us.smt.myfinance.ui.screen.auth.login.LoginPage
import us.smt.myfinance.ui.theme.MyFinanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFinanceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginPage()
                }
            }
        }
    }
}
