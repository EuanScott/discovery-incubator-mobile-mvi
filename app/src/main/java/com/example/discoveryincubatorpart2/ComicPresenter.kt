package com.example.discoveryincubator

import android.util.Log
import com.example.discoveryincubatorpart2.ComicInteractor
import com.example.discoveryincubatorpart2.ComicView
import com.example.discoveryincubatorpart2.ComicViewState
import com.example.discoveryincubatorpart2.models.ComicStateViewModel
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class ComicPresenter() : MviBasePresenter<ComicView, ComicStateViewModel>() {

    private val TAG: String = ComicPresenter::class.java.name
    private val comicInteractor: ComicInteractor = ComicInteractor()

    override fun bindIntents() {
        val getPaymentDetails = intent(ComicView::onCreateLifecycleHook)
            .switchMap(comicInteractor::getIssueList)
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Log.e(TAG, it.message ?: "")
                ComicViewState.Error(it.message ?: "")
            }

        val stateReducer = { _: ComicViewState, changeViewState: ComicViewState -> changeViewState }
        val scanObservable = getPaymentDetails.scan(ComicViewState.Loading, stateReducer)
        subscribeViewState(scanObservable, ComicView::render)
    }
}