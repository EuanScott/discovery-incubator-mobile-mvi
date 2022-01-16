package com.example.discoveryincubatorpart2.models

import android.util.Log

class ComicStateViewModel {
    private var isLoading = false
    private var showComicListing = false
    private var showErrorState = false
    private var comicListing = listOf<Issue>()

    fun copy(): ComicStateViewModel {
        Log.i("tester", "Comics: ${this.comicListing}")
        Log.i("tester", "Loading: ${this.isLoading}")
        return ComicStateViewModel()
            .setIsLoadingState(this.isLoading)
            .setShowComicListingState(this.showComicListing)
            .setShowErrorState(this.showErrorState)
            .setComicListing(this.comicListing)
    }

    fun getIsLoadingState(): Boolean {
        return this.isLoading
    }

    fun setIsLoadingState(isLoading: Boolean): ComicStateViewModel {
        this.isLoading = isLoading
        return this
    }

    fun getShowComicListingState(): Boolean {
        return this.showComicListing
    }

    fun setShowComicListingState(showComicListing: Boolean): ComicStateViewModel {
        this.showComicListing = showComicListing
        return this
    }

    fun getShowErrorState(): Boolean {
        return this.showErrorState
    }

    fun setShowErrorState(showErrorState: Boolean): ComicStateViewModel {
        this.showErrorState = showErrorState
        return this
    }

    fun getComicListing(): List<Issue> {
        return this.comicListing
    }

    fun setComicListing(comicListing: List<Issue>): ComicStateViewModel  {
        this.comicListing = comicListing
        return this
    }
}