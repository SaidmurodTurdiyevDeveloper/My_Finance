package us.smt.myfinance.util

sealed class AuthError:UserError{
    data object UserNotFound:AuthError()
}