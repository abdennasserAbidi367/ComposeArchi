package com.example.composearchitecture

import androidx.lifecycle.ViewModel
import com.example.composearchitecture.navigation.base.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigatorViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator {
}