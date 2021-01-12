package me.miguelos.base.interactors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import me.miguelos.base.util.logger

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
abstract class UseCase<Q : BaseUseCase.RequestValues?, in P : BaseUseCase.ResponseValues?>(
    private val executionSchedulers: ExecutionSchedulers
) : BaseUseCase {

    protected abstract fun buildUseCase(
        requestValues: Q?
    ): Observable<in P>

    protected abstract fun validate(requestValues: Q?): Completable

    fun execute(
        requestValues: Q? = null
    ): Observable<in P> =
        validate(requestValues).andThen(
            Observable.defer {
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
