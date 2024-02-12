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
package com.android.avatatpicker.tests

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.android.avatarpicker.domain.getTempPNG
import com.android.avatarpicker.ui.ResultHandler
import com.android.avatarpicker.ui.details.items.SelectableType
import com.android.avatarpicker.ui.details.items.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeResultHandler : ResultHandler {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.None())
    override val uiState = _uiState.asStateFlow()

    override fun <T : SelectableType> onSelect(result: T) {
        getSelected()?.isSelected = false
        result.isSelected = true
        _uiState.value = UiState.Success(result)
    }

    override fun getSelected() = (uiState.value as? UiState.Success<*>)?.result

    override fun onLoading() {
        _uiState.value = UiState.Loading()
    }

    override fun onError(exception: Exception) {
        _uiState.value = UiState.Error(exception)
    }

    override fun unselect() {
        _uiState.value = UiState.None()
    }

    override fun toResultIntent(context: Context): Intent {
        return Intent()
    }

    override fun getContentUri(): Uri {
        return Uri.EMPTY
    }

    override fun getTempFile() =
        InstrumentationRegistry.getInstrumentation().getContext().getTempPNG("tests.png")

}