package com.example.composearchitecture.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d("TopLevelDestination", "HomeViewModel init")
    }
}