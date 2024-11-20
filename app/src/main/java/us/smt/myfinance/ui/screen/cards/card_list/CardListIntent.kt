package us.smt.myfinance.ui.screen.cards.card_list

sealed interface CardListIntent {
    data object AddCard : CardListIntent
    data object Back : CardListIntent
    data object LoadData : CardListIntent
}