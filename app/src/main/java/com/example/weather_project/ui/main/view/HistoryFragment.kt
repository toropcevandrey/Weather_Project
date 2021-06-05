package com.example.weather_project.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.ui.main.viewmodel.HistoryFragmentViewModel

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryFragmentViewModel

    init{
        Log.d("fragment","history")
    }

    companion object{
        const val TAG = "HISTORY_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryFragmentViewModel::class.java)
    }
}