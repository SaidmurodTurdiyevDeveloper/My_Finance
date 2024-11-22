package us.smt.myfinance.util

sealed class AuthError:UserError{
    data object UserNotRegister:AuthError()
    data object UserNotFound:AuthError()
}