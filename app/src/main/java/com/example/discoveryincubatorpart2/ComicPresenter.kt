package com.example.discoveryincubator

import android.util.Log
import com.example.discoveryincubatorpart2.ComicInteractor
import com.example.discoveryincubatorpart2.ComicState
import com.example.discoveryincubatorpart2.ComicView
import com.example.discoveryincubatorpart2.models.ComicStateViewModel
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers

class ComicPresenter : MviBasePresenter<ComicView, ComicStateViewModel>() {

    override fun bindIntents() {
//        val loadInfo: Observable<ComicState> = intent(ComicView::onCreateLifecycleHook)
//            .switchMap { s -> interactor.getIssueList(IssueService(title = null)) }
//
//        val allIntents: Observable<ComicState> =
//            Observable.mergeArray(loadInfo)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(Consumer<T> { baseState: T ->
////                    logger.info(
////                        za.co.discovery.module.health.ui.landing.fragments.claims.ClaimsHealthLandingPresenter.TAG,
////                        "State $baseState"
////                    )
//                })
//
//        val defaultState = ComicStateViewModel().setIsLoadingState(true)
//
//        // TODO: Get this to work!
//        val stateObservable = allIntents.scan(defaultState, this::viewStateReducer)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//        subscribeViewState(stateObservable, ComicView::render)


        val getPaymentDetails = intent(ComicView::onCreateLifecycleHook)
            .switchMap(ComicInteractor::getIssueList)
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Log.e(this.javaClass.simpleName, it.message ?: "")
                ComicState.Error(it.message ?: "")
            }

        val stateReducer = { _: ComicState, changeState: ComicState -> changeState }
        val scanObservable = getPaymentDetails.scan(ComicState.Loading, stateReducer)
        subscribeViewState(scanObservable, ComicView::render)
    }

//    private fun viewStateReducer(
//        previous: ComicStateViewModel,
//        changes: ComicState
//    ): ComicStateViewModel? {
////        if (changes is ComicsState.Loading) {
////            return previous.copy().setIsLoadingState(true)
////        } else if (changes is ComicsState.ComicsLoaded) {
//        // TODO: Set this to not be hard-coded
//        // val loadedChanges: ComicsState.ComicsLoaded = changes as ComicsState.ComicsLoaded
//
//        return previous.copy()
//            .setIsLoadingState(false)
//            .setShowComicListingState(true)
//            .setShowErrorState(false)
//            .setComicListing(previous.getComicListing())
////        }
//
//        throw IllegalStateException("Don't know how to reduce the partial state $changes")
//    }
}