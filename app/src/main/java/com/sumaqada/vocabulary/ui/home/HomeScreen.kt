package com.sumaqada.vocabulary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.R
import com.sumaqada.vocabulary.repository.UserData
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme
import kotlinx.coroutines.launch

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
            HomeTopAppBar(
                modifier = Modifier,
                userData = userData,
                syncStatus = syncStatus,
                onSyncButtonClicked = onSyncButtonClicked,
                onLogOutButtonClicked = onLogOutButtonClicked
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

                FloatingActionButton(
                    onClick = onFloatingActionButtonClicked,
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }

        }
    ) { innerPadding ->

        when (homeUiState) {
            is HomeUiState.Loading -> {

                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(85.dp))
                }
            }

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

                if (words.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray.copy(alpha = 0.8f))
                            .fillMaxSize(),
                    ) {

                        Text(
                            "No words \n Add new word with the button +",
                            modifier = Modifier.align(
                                Alignment.Center
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(80, 0, 0, 0))
                                .background(Color.White.copy(alpha = 0.2f))
                                .align(Alignment.BottomEnd)
                        )
                    }
                } else {
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


            }

            is HomeUiState.Error -> {}
        }


    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val success = HomeUiState.Success(
        listOf(
            WordHomeUI(1, "Word", "palabra"),
            WordHomeUI(2, "Word", "palabra"),
            WordHomeUI(3, "Word", "palabra"),
            WordHomeUI(4, "Word", "palabra"),
        )
    )
    val loading = HomeUiState.Loading
    VocabularyTheme {
        HomeScreen(
            homeUiState = success,
            userData = null,
            syncStatus = SyncStatus.SYNCHRONIZED
        )

    }
}