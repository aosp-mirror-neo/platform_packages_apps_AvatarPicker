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

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCase
import com.android.avatarpicker.ui.ResultHandler
import com.android.avatarpicker.ui.details.items.SelectableType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    val useCase: GroupedSelectableItemsUseCase,
    val resultHandler: ResultHandler
) : ViewModel() {
    private var _groups: MutableStateFlow<MutableList<MutableList<SelectableType>>> =
        MutableStateFlow(mutableStateListOf())
    val groups = _groups.asStateFlow()

    init {
        viewModelScope.launch {
            _groups.value.addAll(useCase().map { it.toMutableList() })
        }
    }
}