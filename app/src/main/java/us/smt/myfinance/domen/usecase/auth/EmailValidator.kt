package us.smt.myfinance.domen.usecase.auth

import us.smt.myfinance.data.database.local.shared.LocalStorage

@JvmInline
value class EmailValidator(private val localStorage: LocalStorage) {

    operator fun invoke(email: String): Boolean {

        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return regex.matches(email)
    }
}