/*
 * Copyright (C) 2024 The Android Open Source Project
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

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import com.android.avatarpicker.ui.details.items.ItemViewComposer
import com.android.avatarpicker.ui.details.items.SelectorWrapper

fun LazyGridScope.DetailsList(detailsViewModel: DetailsViewModel,
    itemViewComposer: ItemViewComposer) {
    val items = detailsViewModel.groups.value
    var index = 0
    items.forEach { group ->
        val remaining = group.size % 4
        group.forEach { viewModel ->
            index += 1
            item(key = index, contentType = viewModel.typeId) {
                SelectorWrapper(viewModel.isSelected) {
                    itemViewComposer.ItemView(viewModel, detailsViewModel.resultHandler) {
                        detailsViewModel.resultHandler.onSelect(viewModel)
                    }
                }
            }
        }
        if (remaining > 0) {
            item(span = { GridItemSpan(4 - remaining) }) {
                // invisible break between group
            }
        }
    }
}
