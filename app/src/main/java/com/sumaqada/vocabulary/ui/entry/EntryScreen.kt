package com.sumaqada.vocabulary.ui.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("New word") })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                    }
                },
                floatingActionButton = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                    }
                }
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TextField(
                value = "dsfa",
                onValueChange = {},
                singleLine = true,
                maxLines = 1,
                label = { Text("Word") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                maxLines = 1,
                label = { Text("Translated") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = "",
                onValueChange = {},
                singleLine = false,
                maxLines = 5,
                minLines = 3,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }


    }
}

@Preview
@Composable
fun EntryScreenPreview() {
    MaterialTheme {
        EntryScreen()
    }
}