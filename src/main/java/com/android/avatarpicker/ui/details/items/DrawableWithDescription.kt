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

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.avatarpicker.domain.applySelectable

@Composable
fun DrawableWithDescription(
    viewModel: ResourceViewModel, select: () -> Unit
) {
    val painter = painterResource(id = viewModel.drawableId)
    val isSelected: Boolean by viewModel.isSelected.collectAsState()
    Image(painter = painter,
        contentDescription = stringResource(id = viewModel.descriptionId),
        modifier = GetDefaultImageModifier()
            .applySelectable(isSelected, select))
}