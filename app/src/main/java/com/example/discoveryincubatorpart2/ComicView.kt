package com.example.discoveryincubatorpart2

import com.example.discoveryincubatorpart2.models.ComicStateViewModel
import io.reactivex.Observable
import com.hannesdorfmann.mosby3.mvp.MvpView

open interface ComicView : MvpView {
    open fun onCreateLifecycleHook(): Observable<String>
    open fun render(viewState: ComicStateViewModel)
}
