package com.sumaqada.vocabulary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sumaqada.vocabulary.R
import com.sumaqada.vocabulary.repository.UserData
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    userData: UserData?,
    syncStatus: SyncStatus,
    onHomeItemClicked: (Int) -> Unit = {},
    onFloatingActionButtonClicked: () -> Unit = {},
    onSyncButtonClicked: () -> Unit = {},
    onLogOutButtonClicked: () -> Unit = {}
) {

    val lazyState = rememberLazyListState()

    val showScrollButton by remember {
        derivedStateOf { lazyState.firstVisibleItemIndex > 6 }
    }

    val coroutineScope = rememberCoroutineScope()

    val painter = when (syncStatus) {
        SyncStatus.NO_SYNC -> R.drawable.round_cloud_off_24
        SyncStatus.SYNCHRONIZING -> R.drawable.round_cloud_sync_24
        SyncStatus.SYNCHRONIZED -> R.drawable.round_cloud_done_24
        SyncStatus.ERROR -> R.drawable.round_sync_problem_24
    }

    var showMenuUser by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        MaterialTheme.colorScheme.tertiaryContainer,
                    )
                )
            ),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = { Text("Vocabulary") },
                actions = {
                    IconButton(onClick = {
                        if (userData == null) {
                            coroutineScope.launch {
                                onSyncButtonClicked()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(painter),
                            contentDescription = null
                        )
                    }
                    userData?.let {
                        Card(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            onClick = { showMenuUser = !showMenuUser}
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(48.dp),
                                model = it.profilePictureUrl,
                                contentDescription = null,
                            )

                        }
                        DropdownMenu(
                            expanded = showMenuUser,
                            onDismissRequest = {showMenuUser = !showMenuUser}
                        ) {
                            DropdownMenuItem(text = { Text(it.username ?: "no user name") }, enabled = false, onClick = {})
                            DropdownMenuItem(text = { Text("Logout") }, enabled = true, onClick = onLogOutButtonClicked)
                        }
                    }


                }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(showScrollButton) {
                    SmallFloatingActionButton(onClick = {
                        coroutineScope.launch {
                            lazyState.animateScrollToItem(0)
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.round_keyboard_double_arrow_up_24),
                            contentDescription = null
                        )
                    }
                }

                FloatingActionButton(onClick = onFloatingActionButtonClicked) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }

        }
    ) { innerPadding ->

        when (homeUiState) {
            is HomeUiState.Loading -> {}
            is HomeUiState.Success -> {

                val words = homeUiState.words

                val scrollToStart by remember {
                    derivedStateOf {
                        words.size + 1 != lazyState.layoutInfo.totalItemsCount
                    }
                }
                LaunchedEffect(scrollToStart) {
                    if (scrollToStart) {
                        lazyState.animateScrollToItem(0)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(Color.Transparent),
                    state = lazyState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(words, key = { it.id }) { word ->
                        HomeItem(
                            word = word,
                            onClick = onHomeItemClicked
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.size(84.dp))
                    }
                }
            }

            is HomeUiState.Error -> {}
        }


    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    VocabularyTheme {
        HomeScreen(
            homeUiState = HomeUiState.Success(
                listOf(
                    WordHomeUI(1, "Word", "palabra"),
                    WordHomeUI(2, "Word", "palabra"),
                    WordHomeUI(3, "Word", "palabra"),
                    WordHomeUI(4, "Word", "palabra"),
                )
            ),
            userData = null,
            syncStatus = SyncStatus.SYNCHRONIZED
        )

    }
}