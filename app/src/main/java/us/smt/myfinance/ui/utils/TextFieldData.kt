package us.smt.myfinance.ui.utils

import us.smt.myfinance.util.TextViewError


data class TextFieldData(
    val success: Boolean = false,
    val text: String = "",
    val error: TextViewError? = null
)
