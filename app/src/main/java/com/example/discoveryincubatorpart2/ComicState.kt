package com.example.discoveryincubatorpart2

import com.example.discoveryincubatorpart2.models.Issue

sealed class ComicState {

    object Loading : ComicState()

    data class ComicsLoaded(val comics: List<Issue>) : ComicState() {

//        private val showLoader = false
//        private val showError = false
//        private val showComics = false
//
//        fun isShowLoader(): Boolean {
//            return this.showLoader
//        }
//
//        fun isShowError(): Boolean {
//            return this.showError
//        }
//
//        fun isShowComics(): Boolean {
//            return this.showComics
//        }
    }

    data class Error(val error: String) : ComicState()

    object EmptyContent : ComicState()

//    abstract class BaseState : ComicState {
////        override fun equals(obj: Any?): Boolean {
////            return obj != null && this.javaClass.isAssignableFrom(obj.javaClass)
////        }
//
////        override fun hashCode(): Int {
////            return super.hashCode()
////        }
//
////        override fun toString(): String {
////            return this.javaClass.getSimpleName()
////        }
//    }
}