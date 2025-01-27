/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.avatarpicker.ui

import com.android.avatarpicker.domain.applyReadBackOrder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun AdaptivePane(
    showOnePane: Boolean,
    columns: Int,
    startPane: @Composable () -> Unit,
    endPane: (scope: LazyGridScope) -> Unit,
    bottom: @Composable () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (showOnePane) {
                BottomAppBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                    bottom()
                }
            } else {
                Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                    BottomAppBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                        bottom()
                    }
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier.statusBarsPadding().padding(innerPadding)
        ) {
            if (showOnePane) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp),
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column {
                            startPane()
                        }
                    }
                    endPane(this)
                }
            } else {
                Row(
                    modifier = Modifier.statusBarsPadding()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)
                        .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                        .applyReadBackOrder()) { startPane() }

                    LazyVerticalGrid(
                        modifier = Modifier.weight(1f)
                            .padding(top = 104.dp, start = 24.dp, end = 24.dp)
                            .fillMaxHeight(),
                        columns = GridCells.Fixed(columns),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(bottom = 24.dp),
                    ) {
                        endPane(this)
                    }

                }
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            }
        }
    }
}