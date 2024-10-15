package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    val uiState: StateFlow<CountryListUiState> = MutableStateFlow(CountryListUiState.Loading) // TODO implement load
}