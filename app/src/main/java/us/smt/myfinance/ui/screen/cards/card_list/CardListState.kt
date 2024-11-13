package us.smt.myfinance.ui.screen.cards.card_list

import us.smt.myfinance.domen.model.CreditCard

data class CardListState(
    val creditCards: List<CreditCard> = emptyList()
)
