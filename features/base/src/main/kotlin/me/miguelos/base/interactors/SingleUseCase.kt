package me.miguelos.base.interactors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import me.miguelos.base.util.TAG
import me.miguelos.base.util.logger
import timber.log.Timber


abstract class SingleUseCase<Q : BaseUseCase.RequestValues?, in P : BaseUseCase.ResponseValues?>
constructor(private val executionSchedulers: ExecutionSchedulers) : BaseUseCase {

    protected abstract fun buildUseCase(requestValues: Q?): Single<in P>

    protected abstract fun validate(requestValues: Q?): Completable

    fun execute(requestValues: Q? = null): Single<in P> =
        validate(requestValues)
            .andThen(
                Single.defer {
                    buildUseCase(requestValues)
                        .subscribeOn(executionSchedulers.io())
                        .observeOn(executionSchedulers.ui())
                }
            )
            .doOnError {
                throw it
            }

    override fun log(log: String?) {
        logger.i(log)
    }

    override fun logError(error: String?) {
        logger.e(error)
    }
}
