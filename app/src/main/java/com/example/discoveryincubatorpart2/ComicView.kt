package com.example.discoveryincubatorpart2

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface ComicView : MvpView {
    fun render(state: ComicViewState)
    open fun onCreateLifecycleHook(): Observable<String>
}
