package us.smt.myfinance.ui.screen.cards.card_list

sealed interface CardListIntent {
    data object AddCard : CardListIntent
}