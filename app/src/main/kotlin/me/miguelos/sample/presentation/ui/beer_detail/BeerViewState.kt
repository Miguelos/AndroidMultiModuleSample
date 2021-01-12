package me.miguelos.sample.presentation.ui.beer_detail

data class BeerViewState(
    var isLoading: Boolean = false,
    var beerId: Long? = null
)
