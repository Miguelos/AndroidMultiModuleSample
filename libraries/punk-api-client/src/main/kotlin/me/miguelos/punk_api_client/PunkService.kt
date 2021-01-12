package me.miguelos.punk_api_client

import io.reactivex.rxjava3.core.Single
import me.miguelos.punk_api_client.model.BeerEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * REST API access points
 */
interface PunkService {

    @GET("beers/{$ID}")
    fun getBeer(@Path(ID) id: Long): Single<List<BeerEntity>>

    @GET("beers")
    fun getBeers(
        @Query(NAME_QUERY) nameSearch: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(PER_PAGE) perPage: Int? = null
    ): Single<List<BeerEntity>>


    companion object Params {
        const val API_KEY = "apikey"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val NAME_QUERY = "beer_name"
        const val HASH = "hash"
        const val TIMESTAMP = "ts"
        const val ID = "id"
        const val OFFSET = "offset"
        const val LIMIT = "limit"
    }
}
