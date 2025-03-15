package com.sumaqada.vocabulary.ui.home

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sumaqada.vocabulary.R
import com.sumaqada.vocabulary.repository.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    userData: UserData?,
    syncStatus: SyncStatus,
    onSyncButtonClicked: () -> Unit = {},
    onLogOutButtonClicked: () -> Unit = {}
) {

    val painter = when (syncStatus) {
        SyncStatus.NO_SYNC -> R.drawable.round_cloud_off_24
        SyncStatus.SYNCHRONIZING, SyncStatus.FIRST_SYNC -> R.drawable.round_cloud_sync_24
        SyncStatus.SYNCHRONIZED -> R.drawable.round_cloud_done_24
        SyncStatus.ERROR -> R.drawable.round_sync_problem_24
    }

    val tint = when (syncStatus) {
        SyncStatus.SYNCHRONIZED -> Color(0xFF009688)
        else -> LocalContentColor.current
    }

    var showMenuUser by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = { Text("Vocabulary") },
        actions = {
            IconButton(
                onClick = {
                    if (userData == null) {

                        onSyncButtonClicked()

                    }
                },
                enabled = userData == null
            ) {
                Icon(
                    painter = painterResource(painter),
                    contentDescription = null,
                    tint = tint
                )
            }
            userData?.let {
                Card(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    onClick = { showMenuUser = !showMenuUser }
                ) {
                    AsyncImage(
                        modifier = Modifier.size(48.dp),
                        model = it.profilePictureUrl,
                        contentDescription = null,
                    )

                }
                DropdownMenu(
                    expanded = showMenuUser,
                    onDismissRequest = { showMenuUser = !showMenuUser }
                ) {
                    DropdownMenuItem(
                        text = { Text(it.username ?: stringResource(R.string.no_user_name)) },
                        enabled = false,
                        onClick = {})
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        enabled = true,
                        onClick = onLogOutButtonClicked
                    )
                }
            }


        }
    )

}

@Preview
@Composable
private fun HomeTopAppBarPreview() {
    MaterialTheme {
        HomeTopAppBar(
            modifier = Modifier,
            userData = UserData("", "username", "email@", ""),
            syncStatus = SyncStatus.SYNCHRONIZED,
            onSyncButtonClicked = {},
            onLogOutButtonClicked = {}
        )
    }
}