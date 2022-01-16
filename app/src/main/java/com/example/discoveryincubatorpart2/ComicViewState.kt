package com.example.discoveryincubatorpart2

import androidx.constraintlayout.motion.utils.ViewState
import com.example.discoveryincubatorpart2.models.Issue

sealed class ComicViewState : ViewState() {
    object Loading : ComicViewState()
    data class ComicsLoaded(val comics: List<Issue>) : ComicViewState()
    object EmptyContent : ComicViewState()
    data class Error(val error: String) : ComicViewState()
}