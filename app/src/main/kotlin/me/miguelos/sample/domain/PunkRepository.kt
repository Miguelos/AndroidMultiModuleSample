package me.miguelos.sample.domain

import io.reactivex.rxjava3.core.Single
import me.miguelos.sample.domain.usecase.GetBeer
import me.miguelos.sample.domain.usecase.GetBeers

/**
 * Main entry point for accessing Punk data.
 */
interface PunkRepository {

    fun getBeers(
        requestValues: GetBeers.RequestValues
    ): Single<GetBeers.ResponseValues?>

    fun getBeer(
        requestValues: GetBeer.RequestValues
    ): Single<GetBeer.ResponseValues?>
}
