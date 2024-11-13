package us.smt.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import us.smt.myfinance.ui.screen.splash.SplashScreen
import us.smt.myfinance.ui.theme.MyFinanceTheme
import us.smt.myfinance.ui.utils.AppNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFinanceTheme {
                Navigator(SplashScreen()) { navigate ->
                    LaunchedEffect(Unit) {
                        AppNavigator.navigatorState.onEach {
                            it.invoke(navigate)
                        }.launchIn(this)
                    }
                    CurrentScreen()
                }
            }
        }
    }
}
