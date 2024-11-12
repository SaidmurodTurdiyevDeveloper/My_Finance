package us.smt.myfinance.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyFinanceApp:Application() {

    override fun onCreate() {
        super.onCreate()
    }
}