package me.miguelos.sample.presentation.ui.beers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.schedulers.Schedulers
import me.miguelos.base.mappers.TwoWaysMapper
import me.miguelos.base.ui.BaseViewModel
import me.miguelos.base.ui.SingleLiveEvent
import me.miguelos.base.util.logger
import me.miguelos.sample.domain.usecase.GetBeers
import me.miguelos.sample.presentation.model.Beer
import me.miguelos.sample.domain.model.Beer as DomainBeer


class BeersViewModel @ViewModelInject constructor(
    private val getBeers: GetBeers,
    private val beerMapper: TwoWaysMapper<DomainBeer, Beer>
) : BaseViewModel() {

    var viewState: MutableLiveData<BeersViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var beersState: MutableLiveData<List<Beer>> = SingleLiveEvent()


    init {
        viewState.value = BeersViewState(isLoading = false)
    }

    fun loadBeers(query: String? = null, totalItemsCount: Int? = 1) {
        if (viewState.value?.isLoading == false) {
            viewState.value = viewState.value?.copy(isLoading = true)
            if (query?.isNotBlank() == true) {
                viewState.value = viewState.value?.copy(
                    query = query
                )
            }
            performGetBeerList(
                totalItemsCount = totalItemsCount,
                query = viewState.value?.query
            )
        }
    }

    private fun performGetBeerList(
        totalItemsCount: Int? = null,
        query: String? = null
    ) {
        logger.i("Getting beer list (query: \"$query\" items: $totalItemsCount)")

        addDisposable(
            getBeers.execute(
                GetBeers.RequestValues(
                    isForceUpdate = false,
                    query = query,
                    offset = totalItemsCount,
                    limit = if (query == null) {
                        PAGE_SIZE
                    } else {
                        null
                    }
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    beerMapper.mapOptional(
                        (response as? GetBeers.ResponseValues)?.beers
                    )?.toList()?.let {
                        logger.i("Beers received $it")
                        handleBeers(it)
                    }
                    viewState.value = viewState.value?.copy(
                        isLoading = false
                    )
                },
                    {
                        handleError(it)
                        viewState.value = viewState.value?.copy(
                            isLoading = false
                        )
                    }
                )
        )
    }

    private fun handleBeers(beers: List<Beer>) {
        beersState.postValue(beers)
    }

    private fun handleError(it: Throwable) {
        if (it is CompositeException) {
            errorState.postValue(it.exceptions.first())
        } else {
            errorState.postValue(it)
        }
    }

    fun initSearch() {
        viewState.value = viewState.value?.copy(query = null)
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}
