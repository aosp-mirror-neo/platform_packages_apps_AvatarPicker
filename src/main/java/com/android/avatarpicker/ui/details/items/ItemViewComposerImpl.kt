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

package com.android.avatarpicker.ui.details.items

import androidx.compose.runtime.Composable
import com.android.avatarpicker.domain.CAMERA
import com.android.avatarpicker.domain.DEFAULT_ICON
import com.android.avatarpicker.domain.ILLUSTRATION
import com.android.avatarpicker.domain.PHOTO_PICKER
import com.android.avatarpicker.ui.ResultHandler
import com.android.avatarpicker.ui.details.items.media.CameraIcon
import com.android.avatarpicker.ui.details.items.media.PhotoPickerIcon

class ItemViewComposerImpl : ItemViewComposer {
    @Composable
    override fun ItemView(
        viewModel: SelectableType, resultHandler: ResultHandler, select: () -> Unit
    ) {
        when (viewModel) {
            is ResourceViewModel -> when (viewModel.typeId) {
                CAMERA -> CameraIcon(viewModel, resultHandler)
                PHOTO_PICKER -> PhotoPickerIcon(viewModel, resultHandler)
                DEFAULT_ICON -> ColoredIconWithDescription(viewModel) { select() }
                ILLUSTRATION -> DrawableWithDescription(viewModel) { select() }
            }
        }
    }
}
