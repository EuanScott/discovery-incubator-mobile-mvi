package com.example.discoveryincubatorpart2

import com.example.discoveryincubatorpart2.models.Issue
import com.example.discoveryincubatorpart2.network.ComicApi
import com.example.discoveryincubatorpart2.services.IssueService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ComicInteractor {
    fun getIssueList(searchIssues: IssueService): Observable<ComicViewState> {
        return ComicApi.retrofitService.getIssues(searchIssues)
            .switchMap(::getComicState)
            .subscribeOn(Schedulers.io())
            .doOnError {
                ComicViewState.Error(it.message ?: "")
            }
    }

    private fun getComicState(issues: List<Issue>): Observable<ComicViewState> {
        return Observable.just(ComicViewState.ComicsLoaded(issues))
    }
}