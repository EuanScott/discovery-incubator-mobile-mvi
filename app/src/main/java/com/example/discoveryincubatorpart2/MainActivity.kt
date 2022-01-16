package com.example.discoveryincubatorpart2

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.ComicPresenter
import com.example.discoveryincubatorpart2.adapters.IssueAdapter
import com.example.discoveryincubatorpart2.models.Issue
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

public class MainActivity : MviActivity<ComicView, ComicPresenter>(), ComicView {

    private val TAG: String = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun render(comicViewState: ComicViewState) {
        when (comicViewState) {
            is ComicViewState.Loading -> {
                Log.i(TAG, "Still loading")
            }
            is ComicViewState.ComicsLoaded -> {
                Log.i(TAG, "Displaying Comics")
                // onSuccessIssuesReceived(viewState.getComicListing())
            }
            is ComicViewState.EmptyContent -> {
                Log.i(TAG, "No Comics")
            }
            is ComicViewState.Error -> {
                Log.i(TAG, "Error loading comics")
                // onSuccessIssuesReceived(listOf())
            }
        }
    }

    // TODO: Get this to work
    override fun onCreateLifecycleHook(): Observable<String> = null

    override fun createPresenter(): ComicPresenter {
        return ComicPresenter()
    }

    private fun onSuccessIssuesReceived(issues: List<Issue>) {
        if (issues.isNotEmpty()) {
//            runOnUiThread {
//                if (adapterObservable != null && !adapterObservable!!.isDisposed) {
//                    adapterObservable!!.dispose()
//                }

            val issueAdapter = context?.let { IssueAdapter(it, issues) }
            rvIssues.adapter = issueAdapter
            rvIssues.layoutManager = LinearLayoutManager(context)

//                adapterObservable = issueAdapter
//                    .userInteraction()
//                    .subscribe { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
//            }
        } else {
            displayToast("An unexpected error occurred. Please try again.")
        }
    }

    private fun onErrorNoIssues(error: Throwable) {
        Log.i(TAG, "onError: $error")
        displayToast("Unable to fetch Issue data. Please try again")
    }

    // https://stackoverflow.com/a/12897386
    private fun displayToast(toastMessage: String) {
        Log.i(TAG, "toastMessage: $toastMessage")
//        runOnUiThread {
//            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
//        }
    }
}