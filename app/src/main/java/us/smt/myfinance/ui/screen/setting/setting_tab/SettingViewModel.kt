package us.smt.myfinance.ui.screen.setting.setting_tab

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.ui.screen.setting.about.AboutScreen
import us.smt.myfinance.ui.screen.setting.help.HelpScreen
import us.smt.myfinance.ui.screen.setting.language.LanguageScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(navigator: AppNavigator, localStorage: LocalStorage) : BaseViewModel<SettingState, SettingIntent>(SettingState(), navigator) {
    init {
        update(state = state.value.copy(name = localStorage.name, surname = localStorage.lastName))
    }

    override fun onAction(intent: SettingIntent) {
        when (intent) {
            SettingIntent.OpenAbout -> openAbout()
            SettingIntent.OpenHelp -> openHelp()
            SettingIntent.OpenLanguage -> openLanguage()
        }
    }

    private fun openAbout() {
        navigate(AboutScreen())
    }

    private fun openHelp() {
        navigate(HelpScreen())
    }

    private fun openLanguage() {
        navigate(LanguageScreen())
    }

}