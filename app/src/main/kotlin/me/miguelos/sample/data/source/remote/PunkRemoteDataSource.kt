package me.miguelos.sample.data.source.remote

import io.reactivex.rxjava3.core.Single
import me.miguelos.base.mappers.Mapper
import me.miguelos.punk_api_client.PunkService
import me.miguelos.punk_api_client.model.BeerEntity
import me.miguelos.sample.data.exceptions.ServerError
import me.miguelos.sample.data.source.PunkRemoteDataSource
import me.miguelos.sample.domain.model.Beer
import me.miguelos.sample.domain.usecase.GetBeer
import me.miguelos.sample.domain.usecase.GetBeers
import javax.inject.Inject

/**
 * Implementation of the data source that connects to Punk API.
 */
class PunkRemoteDataSource @Inject constructor(
    private val punkService: PunkService,
    private val beerMapper: Mapper<BeerEntity, Beer>
) : PunkRemoteDataSource {

    override fun getBeers(
        requestValues: GetBeers.RequestValues
    ): Single<GetBeers.ResponseValues?> =
        punkService.getBeers(
            requestValues.query?.replace(" ", "_"),
            requestValues.offset,
            requestValues.limit
        )
            .map { response ->
                when {
                    response.isNotEmpty() -> {
                        GetBeers.ResponseValues(
                            beerMapper.mapOptional(response)?.toList()
                        )
                    }
                    else -> {
                        GetBeers.ResponseValues(
                            error = ServerError("No data")
                        )
                    }
                }
            }

    override fun getBeer(
        requestValues: GetBeer.RequestValues
    ): Single<GetBeer.ResponseValues?> =
        punkService.getBeer(requestValues.id)
            .map { response ->
                when {
                    response.isNotEmpty() -> {
                        GetBeer.ResponseValues(
                            beerMapper.mapOptional(response?.get(0))
                        )
                    }
                    else -> {
                        GetBeer.ResponseValues(
                            error = ServerError("No data")
                        )
                    }
                }
            }
}
