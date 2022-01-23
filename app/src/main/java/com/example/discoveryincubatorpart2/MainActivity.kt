package com.example.discoveryincubatorpart2

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubatorpart2.adapters.IssueAdapter
import com.example.discoveryincubatorpart2.models.Issue
import com.example.discoveryincubatorpart2.services.IssueService
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MviActivity<ComicView, ComicPresenter>(), ComicView {

    private val TAG: String = MainActivity::class.java.name

    override val listComic: PublishSubject<IssueService> = PublishSubject.create()
    override val searchComic: PublishSubject<IssueService> = PublishSubject.create()

    private var adapterObservable: Disposable? = null

    enum class ViewState {
        VISIBLE,
        GONE,
        INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        bSearch.setOnClickListener { searchComic.onNext(IssueService(etSearchInput.text.toString())) }
        listComic.onNext(IssueService(null))
    }

    override fun createPresenter(): ComicPresenter {
        return ComicPresenter()
    }

    override fun render(comicViewState: ComicViewState) {
        when (comicViewState) {
            is ComicViewState.Loading -> displayContent(ViewState.GONE)
            is ComicViewState.ComicsLoaded -> {
                // Note: I just have this delay in so that you can see the loader being displayed
                Handler().postDelayed({
                    onSuccessIssuesReceived(comicViewState.comics)
                    displayContent(ViewState.VISIBLE)
                }, 3000)
            }
            is ComicViewState.Error -> {
                onErrorNoIssues(comicViewState.error)
                displayContent(ViewState.VISIBLE)
            }

        }
    }

    private fun displayContent(viewState: ViewState) {
        when (viewState) {
            ViewState.VISIBLE -> {
                progressBar.visibility = View.GONE
                search.visibility = View.VISIBLE
                rvIssues.visibility = View.VISIBLE
            }
            ViewState.GONE -> {
                progressBar.visibility = View.VISIBLE
                search.visibility = View.GONE
                rvIssues.visibility = View.GONE
            }
            ViewState.INVISIBLE -> {
                progressBar.visibility = View.INVISIBLE
                search.visibility = View.VISIBLE
                rvIssues.visibility = View.VISIBLE
            }
        }
    }

    private fun onSuccessIssuesReceived(issues: List<Issue>) {
        if (issues.isNotEmpty()) {
            runOnUiThread {
                if (adapterObservable != null && !adapterObservable!!.isDisposed) {
                    adapterObservable!!.dispose()
                }

                val issueAdapter = IssueAdapter(this, issues)
                rvIssues.adapter = issueAdapter
                rvIssues.layoutManager = LinearLayoutManager(this)

                adapterObservable = issueAdapter
                    .userInteraction()
                    .subscribe {
                        displayToast(it)
                    }
            }
        } else {
            displayToast("An unexpected error occurred. Please try again.")
        }
    }

    private fun onErrorNoIssues(error: String) {
        Log.e(TAG, "onError: $error")
        displayToast("Unable to fetch Issue data. Please try again")
    }

    // https://stackoverflow.com/a/12897386
    private fun displayToast(toastMessage: String) {
        runOnUiThread {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
