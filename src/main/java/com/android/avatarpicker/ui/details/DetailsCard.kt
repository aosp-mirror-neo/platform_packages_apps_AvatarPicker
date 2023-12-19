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

package com.android.avatarpicker.ui.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.avatarpicker.AvatarProviderApp
import com.android.avatarpicker.ui.details.items.SelectorWrapper

@Composable
fun DetailsCard(detailsViewModel: DetailsViewModel, modifier: Modifier = Modifier) {
    val state by detailsViewModel.groups.collectAsState()
    // This is needed so composable functionality can be extended by overriding apps
    val itemComposer =
        (LocalContext.current.applicationContext as AvatarProviderApp).getItemViewComposer()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 104.dp, bottom = 24.dp)
    ) {
        var index = 0
        state.forEach { group ->
            val remaining = group.size - ((group.size shr 2) shl 2)
            group.forEach { viewModel ->
                index += 1
                item(key = index, contentType = viewModel.typeId) {
                    SelectorWrapper(viewModel.isSelected) {
                        itemComposer.ItemView(viewModel, detailsViewModel.resultHandler) {
                            detailsViewModel.resultHandler.onSelect(viewModel)
                        }
                    }
                }
            }
            if (remaining > 0) {
                item(span = { GridItemSpan(4 - remaining) }) {
                    // invisible break between groups
                }
            }
        }
    }
}