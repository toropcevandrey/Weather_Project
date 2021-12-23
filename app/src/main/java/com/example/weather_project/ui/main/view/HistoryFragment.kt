package com.example.weather_project.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_project.R
import com.example.weather_project.ui.App
import com.example.weather_project.ui.main.viewmodel.HistoryFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HistoryFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: HistoryFragmentViewModel? = null
    private lateinit var rvHistory: RecyclerView
    private lateinit var adapterHistory: HistoryListAdapter
    private lateinit var fab: FloatingActionButton

    companion object {
        const val TAG = "HISTORY_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.getComponent().inject(this)
        val view: View = inflater.inflate(R.layout.history_fragment, container, false)
        initViews(view)
        setupHistoryViewModel()
        setObservers()
        setBackground()
        return view
    }

    private fun initViews(view: View): View {
        rvHistory = view.findViewById(R.id.rv_history_rv)
        rvHistory.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapterHistory = HistoryListAdapter()
        rvHistory.adapter = adapterHistory
        rvHistory.layoutManager = LinearLayoutManager(activity)
        fab = view.findViewById(R.id.fab_history_del)
        fab.setOnClickListener {
            viewModel?.deleteAll()
        }
        return view
    }

    private fun setupHistoryViewModel() {
        viewModel = ViewModelProvider(this, factory).get(HistoryFragmentViewModel::class.java)
    }

    private fun setObservers() {
        viewModel?.allWeatherData?.observe(viewLifecycleOwner) { weather ->
            adapterHistory.submitList(weather)
        }
    }

    private fun setBackground() {
        if (LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"))
                .toInt() in 6..18
        ) {
            rvHistory.setBackgroundResource(R.drawable.day)
        } else {
            rvHistory.setBackgroundResource(R.drawable.night)
        }
    }
}