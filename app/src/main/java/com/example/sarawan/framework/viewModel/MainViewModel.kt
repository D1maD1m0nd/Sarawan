package com.example.sarawan.framework.viewModel

import com.example.sarawan.framework.MainInteractor
import com.example.sarawan.framework.ui.base.BaseViewModel
import com.example.sarawan.model.data.AppState
import com.example.sarawan.rx.ISchedulerProvider
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractor,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel<AppState>() {

    fun search(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .doOnSubscribe { stateLiveData.value = AppState.Loading }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver() = object : DisposableObserver<AppState>() {
        override fun onNext(appState: AppState) {
            stateLiveData.value = appState
        }

        override fun onError(e: Throwable) {
            stateLiveData.value = AppState.Error(e)
        }

        override fun onComplete() = Unit
    }
}