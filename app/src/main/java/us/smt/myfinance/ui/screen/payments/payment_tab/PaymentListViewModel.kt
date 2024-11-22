package us.smt.myfinance.ui.screen.payments.payment_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import us.smt.myfinance.ui.screen.payments.payment.PaymentScreen
import us.smt.myfinance.ui.utils.AppNavigator
import javax.inject.Inject

@HiltViewModel
class PaymentListViewModel @Inject constructor(private val navigator: AppNavigator) : ViewModel() {

    fun openPayment(int: Int) {
        viewModelScope.launch {
            navigator.navigateTo(
                PaymentScreen(int)
            )
        }
    }
}