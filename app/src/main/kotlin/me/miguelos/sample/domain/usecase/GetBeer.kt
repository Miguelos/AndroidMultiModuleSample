package me.miguelos.sample.domain.usecase

import androidx.annotation.VisibleForTesting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import me.miguelos.sample.domain.PunkRepository
import me.miguelos.base.interactors.BaseUseCase
import me.miguelos.base.interactors.ExecutionSchedulers
import me.miguelos.base.interactors.SingleUseCase
import me.miguelos.sample.domain.model.Beer
import javax.inject.Inject


/**
 * Fetches the list of Punk API beers.
 */
class GetBeer @Inject constructor(
    executionSchedulers: ExecutionSchedulers,
    private val punkRepositoryPunk: PunkRepository
) : SingleUseCase<GetBeer.RequestValues?, GetBeer.ResponseValues?>(executionSchedulers) {

    class RequestValues(val isForceUpdate: Boolean, val id: Long) : BaseUseCase.RequestValues

    class ResponseValues(
        val beer: Beer? = null,
        val error: Throwable? = null
    ) : BaseUseCase.ResponseValues

    override fun buildUseCase(requestValues: RequestValues?): Single<ResponseValues?> =
        punkRepositoryPunk.getBeer(requestValues!!)

    @VisibleForTesting
    public override fun validate(requestValues: RequestValues?): Completable =
        if (requestValues?.id != null) {
            Completable.complete()
        } else {
            Completable.error(IllegalArgumentException("Request values must be provided."))
        }
}
