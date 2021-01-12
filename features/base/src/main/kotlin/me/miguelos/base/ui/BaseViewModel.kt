package me.miguelos.base.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import me.miguelos.base.util.logger


abstract class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun addDisposable(disposable: Disposable) {
        logger.d("Add disposable $disposable")
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
