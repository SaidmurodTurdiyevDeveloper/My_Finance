package us.smt.myfinance.util

sealed class EmailError:TextViewError{
    data object Empty:EmailError()
    data object Invalid:EmailError()
}
