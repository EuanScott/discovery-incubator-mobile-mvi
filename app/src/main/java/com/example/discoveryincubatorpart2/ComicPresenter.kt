package com.example.discoveryincubatorpart2

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class ComicPresenter() : MviBasePresenter<ComicView, ComicViewState>() {

    private val TAG: String = ComicPresenter::class.java.name
    private val comicInteractor: ComicInteractor = ComicInteractor()

    override fun bindIntents() {
        val getIssueList = intent(ComicView::listComic)
            .switchMap(comicInteractor::getIssueList)
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                ComicViewState.Error(it.message ?: "Unable to get a list of comics.")
            }

        val getSearchResults = intent(ComicView::searchComic)
            .switchMap(comicInteractor::getIssueList)
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                ComicViewState.Error(it.message ?: "Unable to search for your comic.")
            }

        val allIntents: Observable<ComicViewState> = Observable.mergeArray(getIssueList, getSearchResults)
        val stateReducer = { _: ComicViewState, changeViewState: ComicViewState -> changeViewState }
        val scanObservable = allIntents.scan(ComicViewState.Loading, stateReducer)
        subscribeViewState(scanObservable, ComicView::render)
    }
}
