package us.smt.myfinance.util

sealed class EmailError:UserError{
    data object Empty:EmailError()
    data object Invalid:EmailError()
}
