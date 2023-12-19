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

package com.android.avatarpicker.ui.details.items.media

import android.net.Uri
import android.util.TypedValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.android.avatarpicker.domain.getCropIntent
import com.android.avatarpicker.ui.ResultHandler
import com.android.avatarpicker.ui.details.items.ResourceViewModel
import com.android.avatarpicker.ui.details.items.UiState
import com.android.avatarpicker.ui.details.items.UriTypedItem

@Composable
fun CameraIcon(viewModel: ResourceViewModel, resultHandler: ResultHandler) {
    val cropResult = rememberLauncherForCropActivity { uri ->
        uri?.let { resultHandler.onSelect(UriTypedItem(viewModel.typeId, it)) } ?: UiState.None()
    }
    val contentUri: Uri = resultHandler.getContentUri()
    val avatarSizeInPixels = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 190f, LocalContext.current.resources.displayMetrics
    ).toInt()

    val cropIntent = getCropIntent(contentUri, avatarSizeInPixels)

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cropResult.launch(cropIntent)
            } else {
                resultHandler.unselect()
            }
        }

    MediaIcon(viewModel) {
        resultHandler.onLoading()
        cameraLauncher.launch(contentUri)
    }
}