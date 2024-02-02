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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.avatarpicker.AvatarProviderApp
import com.android.avatarpicker.ui.bottombar.BottomActionBar
import com.android.avatarpicker.ui.details.DetailsCard
import com.android.avatarpicker.ui.details.items.UiState
import com.android.avatarpicker.ui.info.InfoCard
import com.android.avatarpicker.ui.theme.AvatarPickerTheme

class AvatarPickerActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val activityViewModel = (application as AvatarProviderApp).let {
            ActivityViewModel(it.getGroupedSelectableItems(intent), it.getResultHandler())
        }

        setContent {
            AvatarPickerTheme {
                val windowSize = calculateWindowSizeClass(this)
                Surface(
                    contentColor = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    val currentResult by activityViewModel.resultHandler.uiState.collectAsState()
                    val showOnePane = (windowSize.widthSizeClass != WindowWidthSizeClass.Expanded)
                    Box(
                        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {

                        AdaptivePane(showOnePane = showOnePane,
                            startPane = { InfoCard(activityViewModel.infoViewModel) },
                            endPane = { DetailsCard(activityViewModel.detailsViewModel) },
                            bottom = {
                            BottomActionBar(currentResult is UiState.Success<*>, {
                                val result = activityViewModel.resultHandler.toResultIntent(
                                    this@AvatarPickerActivity
                                )
                                activityViewModel.resultHandler.unselect()
                                this@AvatarPickerActivity.setResult(RESULT_OK, result)
                                this@AvatarPickerActivity.finish()
                            }, {
                                activityViewModel.resultHandler.unselect()
                                this@AvatarPickerActivity.finish()
                            })
                        })

                        if (currentResult is UiState.Loading) {
                            Box(
                                Modifier.fillMaxSize()
                                    .background(MaterialTheme.colorScheme.onSurface.copy(.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(64.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
