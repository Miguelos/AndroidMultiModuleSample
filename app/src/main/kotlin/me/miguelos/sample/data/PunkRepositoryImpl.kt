package me.miguelos.sample.data

import io.reactivex.rxjava3.core.Single
import me.miguelos.sample.data.source.remote.PunkRemoteDataSource
import me.miguelos.sample.domain.PunkRepository
import me.miguelos.sample.domain.usecase.GetBeer
import me.miguelos.sample.domain.usecase.GetBeers
import javax.inject.Inject

class PunkRepositoryImpl @Inject constructor(
    private val punkRemoteDS: PunkRemoteDataSource
) : PunkRepository {

    /**
     * Gets Punk beers from cache, local data source (SQLite) or remote data source,
     * in that order or preference.
     */
    override fun getBeers(
        requestValues: GetBeers.RequestValues
    ): Single<GetBeers.ResponseValues?> =
        punkRemoteDS.getBeers(requestValues)

    /**
     * Gets beers from local data source (sqlite) unless the table is new or empty.
     * In that case it uses the network data source. This is done to simplify the sample.
     */
    override fun getBeer(
        requestValues: GetBeer.RequestValues
    ): Single<GetBeer.ResponseValues?> =
        punkRemoteDS.getBeer(requestValues)
}
