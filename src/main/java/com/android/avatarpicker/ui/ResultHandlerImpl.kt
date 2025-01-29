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

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.android.avatarpicker.R
import com.android.avatarpicker.domain.getFileUri
import com.android.avatarpicker.domain.getTempPNG
import com.android.avatarpicker.domain.toIntent
import com.android.avatarpicker.ui.details.items.ResourceViewModel
import com.android.avatarpicker.ui.details.items.SelectableType
import com.android.avatarpicker.ui.details.items.UiState
import com.android.avatarpicker.ui.details.items.UriTypedItem
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue

class ResultHandlerImpl(context: Context) : ResultHandler {
    private val resultFile: File
    private val contentUri: Uri
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.None())
    override val uiState = _uiState.asStateFlow()

    override fun <T : SelectableType> onSelect(result: T) {
        getSelected()?.unselect()
        result.select()
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

    init {
        context.apply {
            resultFile = getTempPNG(getString(R.string.result_file_name))
            contentUri = getFileUri(resultFile)
            val packages = resources.getStringArray(R.array.grant_read_uri_permissions_packages)
            packages.forEach { packageName ->
                grantUriPermission(
                    packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    }

    override fun getContentUri() = contentUri
    override fun getTempFile() = resultFile

    override fun toResultIntent(context: Context): Intent {
        getSelected().let { result ->
            return when (result) {
                is ResourceViewModel -> result.toIntent(context)
                is UriTypedItem -> result.toIntent()
                else -> Intent()
            }
        }
    }
}
