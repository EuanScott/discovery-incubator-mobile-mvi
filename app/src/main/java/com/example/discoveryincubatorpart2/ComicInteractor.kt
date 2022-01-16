package com.example.discoveryincubatorpart2

import android.util.Log
import com.example.discoveryincubatorpart2.models.Issue
import com.example.discoveryincubatorpart2.network.ComicApi
import com.example.discoveryincubatorpart2.services.IssueService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ComicInteractor {
    private val TAG = ComicInteractor::class.java.simpleName

    fun getIssueList(searchIssues: IssueService): Observable<ComicState> {
        return ComicApi.retrofitService.getIssues(searchIssues)
            .switchMap(::getComicState)
            .subscribeOn(Schedulers.io())
            .doOnError { Log.e(TAG, it.message ?: "") }
    }

    private fun getComicState(issues: List<Issue>): Observable<ComicState> {
        return Observable.just(ComicState.ComicsLoaded(issues))
    }
}