package com.example.discoveryincubatorpart2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubatorpart2.adapters.IssueAdapter
import com.example.discoveryincubatorpart2.databinding.ActivityMainBinding
import com.example.discoveryincubatorpart2.models.ComicStateViewModel
import com.example.discoveryincubatorpart2.models.Issue
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class ComicFragment : Fragment(), ComicView {

    private val TAG: String = ComicFragment::class.java.name
    private lateinit var binding: ActivityMainBinding
    private val onCreateLifeCycleSubject = PublishSubject.create<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view?.let { super.onViewCreated(it, savedInstanceState) }
        binding = ActivityMainBinding.inflate(layoutInflater)

        Log.i(TAG, "ComicsFragment -> onCreateView")
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onCreateLifecycleHook(): Observable<String> {
        return onCreateLifeCycleSubject
    }

    override fun render(viewState: ComicStateViewModel) {
        try {
            if (viewState.getIsLoadingState()) {
                Log.i(TAG, "Still loading")
            }

            if (viewState.getShowComicListingState()) {
                Log.i(TAG, "Displaying Comics")
                // onSuccessIssuesReceived(viewState.getComicListing())
            }

            if (viewState.getShowErrorState()) {
                Log.i(TAG, "Error loading comics")
                // onSuccessIssuesReceived(listOf())
            }
        } catch (e: Exception) {
            Log.i(TAG, "error: $e")
        }
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

            // TODO: User click event
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