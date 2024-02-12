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
package com.android.avatarpicker.tests.ui

import com.android.avatarpicker.domain.CAMERA
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCase
import com.android.avatarpicker.domain.ILLUSTRATION
import com.android.avatarpicker.domain.PHOTO_PICKER
import com.android.avatarpicker.tests.R
import com.android.avatarpicker.ui.details.items.ResourceViewModel
import com.android.avatarpicker.ui.details.items.SelectableType

class IllustrationsFakeUseCase(private val description: String?) : GroupedSelectableItemsUseCase {
    val list1: List<SelectableType> = listOf(
        ResourceViewModel(PHOTO_PICKER,
            R.drawable.ic_avatar_choose_photo,
            R.string.user_image_choose_photo, null),
        ResourceViewModel(CAMERA,
            R.drawable.ic_avatar_take_photo,
            R.string.user_image_take_photo, null))
    val list2: List<SelectableType> = listOf(
        ResourceViewModel(ILLUSTRATION, R.drawable.light_blue,
            R.string.light_blue, null),
        ResourceViewModel(ILLUSTRATION, R.drawable.light_yellow,
            R.string.light_yellow, null),
        ResourceViewModel(ILLUSTRATION, R.drawable.white,
            R.string.white, null))

    override suspend fun invoke() = listOf(list1, list2)
    override suspend fun description() = description

}