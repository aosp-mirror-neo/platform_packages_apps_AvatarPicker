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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdaptivePane(
    showOnePane: Boolean,
    startPane: @Composable () -> Unit,
    endPane: @Composable () -> Unit,
    bottom: @Composable () -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) { bottom() }
    }) { innerPadding ->
        Column(
            modifier = Modifier.statusBarsPadding().padding(innerPadding)
        ) {
            if (showOnePane) {
                Column(
                    modifier = Modifier.padding(start = 48.dp, end = 48.dp, top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    startPane()
                    endPane()
                }
            } else {
                Row(
                    modifier = Modifier.statusBarsPadding()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .padding(start = 24.dp, end = 24.dp, top = 8.dp)
                    ) { startPane() }
                    Column(
                        modifier = Modifier.weight(1f).padding(start = 24.dp, end = 24.dp)
                            .fillMaxHeight()
                    ) { endPane() }
                }
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            }
        }
    }
}