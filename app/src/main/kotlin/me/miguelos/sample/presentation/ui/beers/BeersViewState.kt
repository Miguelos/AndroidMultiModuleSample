package me.miguelos.sample.presentation.ui.beers

data class BeersViewState(
    var isLoading: Boolean = false,
    var query: String? = null
)
