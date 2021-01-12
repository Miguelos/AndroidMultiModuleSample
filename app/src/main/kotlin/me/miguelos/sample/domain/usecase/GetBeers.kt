package me.miguelos.sample.domain.usecase

import androidx.annotation.VisibleForTesting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import me.miguelos.base.interactors.BaseUseCase
import me.miguelos.base.interactors.ExecutionSchedulers
import me.miguelos.base.interactors.SingleUseCase
import me.miguelos.sample.domain.PunkRepository
import me.miguelos.sample.domain.model.Beer
import javax.inject.Inject

/**
 * Fetches the list of Punk beers.
 */
class GetBeers @Inject constructor(
    executionSchedulers: ExecutionSchedulers,
    private val punkRepository: PunkRepository
) : SingleUseCase<GetBeers.RequestValues?, GetBeers.ResponseValues?>(executionSchedulers) {

    class RequestValues(
        val isForceUpdate: Boolean = false,
        val query: String? = null,
        val offset: Int? = null,
        val limit: Int? = null,
        val saved: Boolean = false
    ) : BaseUseCase.RequestValues

    class ResponseValues(
        val beers: List<Beer>? = null,
        val error: Throwable? = null
    ) : BaseUseCase.ResponseValues

    override fun buildUseCase(requestValues: RequestValues?): Single<ResponseValues?> =
        punkRepository.getBeers(requestValues!!)

    @VisibleForTesting
    public override fun validate(requestValues: RequestValues?): Completable =
        if (requestValues?.limit != null || requestValues?.query != null) {
            Completable.complete()
        } else {
            Completable.error(IllegalArgumentException("Request values must be provided."))
        }
}
