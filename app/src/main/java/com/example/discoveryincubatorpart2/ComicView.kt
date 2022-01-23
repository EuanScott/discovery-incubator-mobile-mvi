package com.example.discoveryincubatorpart2

import com.example.discoveryincubatorpart2.services.IssueService
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.subjects.PublishSubject

interface ComicView : MvpView {
    fun render(state: ComicViewState)
    val listComic: PublishSubject<IssueService>
    val searchComic: PublishSubject<IssueService>
}
