package us.smt.myfinance.ui.screen.setting.setting_tab

sealed interface SettingIntent {
    data object OpenAbout : SettingIntent
    data object OpenHelp : SettingIntent
    data object OpenLanguage : SettingIntent
}