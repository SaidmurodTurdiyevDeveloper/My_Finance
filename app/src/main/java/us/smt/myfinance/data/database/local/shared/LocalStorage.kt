package us.smt.myfinance.data.database.local.shared

import android.app.Application
import us.smt.myfinance.domen.model.DebtOweData
import javax.inject.Inject

class LocalStorage @Inject constructor(
    private val application: Application
) {
    private val sharedPreferences = application.getSharedPreferences(
        "myfinance",
        0
    )
    var isRegistered: Boolean by BooleanPreference(sharedPreferences, false)
    var isLogin: Boolean by BooleanPreference(sharedPreferences, false)
    var name:String by StringPreference(sharedPreferences, "")
    var lastName:String by StringPreference(sharedPreferences, "")
    var email:String by StringPreference(sharedPreferences, "")
    var password:String by StringPreference(sharedPreferences, "")
    var owes:String by StringPreference(sharedPreferences, "")
    var cards:String by StringPreference(sharedPreferences, "")
    var debts:String by StringPreference(sharedPreferences, "")
    var costs:String by StringPreference(sharedPreferences, "")
}