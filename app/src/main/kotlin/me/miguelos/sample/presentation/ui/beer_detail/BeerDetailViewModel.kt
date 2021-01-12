package me.miguelos.sample.presentation.ui.beer_detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.schedulers.Schedulers
import me.miguelos.base.mappers.TwoWaysMapper
import me.miguelos.base.util.logger
import me.miguelos.sample.domain.usecase.GetBeer
import me.miguelos.base.ui.BaseViewModel
import me.miguelos.base.ui.SingleLiveEvent
import me.miguelos.sample.presentation.model.Beer
import me.miguelos.sample.domain.model.Beer as DomainBeer

class BeerDetailViewModel @ViewModelInject constructor(
    private val getBeer: GetBeer,
    private val beerMapper: TwoWaysMapper<DomainBeer, Beer>
) : BaseViewModel() {

    var viewState: MutableLiveData<BeerViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var beerState: MutableLiveData<Beer> = SingleLiveEvent()


    init {
        viewState.value = BeerViewState(isLoading = false)
    }

    fun saveId(id: Long) {
        viewState.value = viewState.value?.copy(beerId = id)
        getBeer()
    }

    private fun getBeer() {
        if (viewState.value?.isLoading == false && validateData()) {
            viewState.value = viewState.value?.copy(isLoading = true)
            viewState.value?.beerId?.let { performGetBeerList(it) }
        }
    }

    private fun validateData() = viewState.value?.beerId != null

    private fun performGetBeerList(id: Long) {
        logger.i("Getting beers list")

        addDisposable(getBeer
            .execute(GetBeer.RequestValues(false, id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    beerMapper.mapOptional(
                        (response as? GetBeer.ResponseValues)?.beer
                    )?.let {
                        logger.i("Beer received $it")
                        handleBeer(it)
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

    private fun handleBeer(beers: Beer) {
        beerState.postValue(beers)
    }

    private fun handleError(it: Throwable) {
        if (it is CompositeException) {
            errorState.postValue(it.exceptions.first())
        } else {
            errorState.postValue(it)
        }
    }
}
