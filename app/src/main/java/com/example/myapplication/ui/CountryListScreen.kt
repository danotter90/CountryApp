@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.api.countryApi
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.flow


@Composable
fun CountryScreen(
    uiState: CountryListUiState
) {
    CountryScreenUi(uiState = uiState)
}

sealed interface CountryListUiState {

    data class Items(val items: List<String>) : CountryListUiState

    data object Loading : CountryListUiState
}

@Composable
private fun CountryScreenUi(
    uiState: CountryListUiState
) {
    MyApplicationTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Countries") },
                    actions = {
                        IconButton(onClick = {
                            /* TODO implement refresh */
                        }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                )
            }
        ) { contentPadding ->
            when (uiState) {
                CountryListUiState.Loading -> {
                    LoadingIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                    )
                }

                is CountryListUiState.Items -> {
                    CountriesItems(
                        items = uiState.items,
                        contentPadding = contentPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun CountriesItems(
    items: List<String>,
    contentPadding: PaddingValues
) {
    LazyColumn(contentPadding = contentPadding) {
        items(items) { item ->
            ListItem(
                headlineContent = { Text(item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
            )
        }
    }
}

@Composable
private fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading...")

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressIndicator()
    }
}
