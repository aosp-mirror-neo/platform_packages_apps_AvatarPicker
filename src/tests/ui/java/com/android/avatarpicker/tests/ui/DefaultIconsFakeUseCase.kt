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
import com.android.avatarpicker.domain.DEFAULT_ICON
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCase
import com.android.avatarpicker.domain.PHOTO_PICKER
import com.android.avatarpicker.tests.R
import com.android.avatarpicker.ui.details.items.ResourceViewModel
import com.android.avatarpicker.ui.details.items.SelectableType

class DefaultIconsFakeUseCase(private val description: String?) : GroupedSelectableItemsUseCase {
    val list: List<SelectableType> = listOf(
        ResourceViewModel(PHOTO_PICKER,
            R.drawable.ic_avatar_choose_photo,
            R.string.user_image_choose_photo, null),
        ResourceViewModel(CAMERA,
            R.drawable.ic_avatar_take_photo,
            R.string.user_image_take_photo, null),
        defaultViewModel(0xffe46962.toInt()),
        defaultViewModel(0xffaf5cf7.toInt()),
        defaultViewModel(0xff4c8df6.toInt()),
        defaultViewModel(0xfff439a0.toInt()),
        defaultViewModel(0xff1ea446.toInt()),
        defaultViewModel(0xff129eaf.toInt()),
        defaultViewModel(0xffb26c00.toInt()),
        defaultViewModel(0xffe8710a.toInt()))

    override suspend fun invoke() = listOf(list)
    override suspend fun description() = description

    private fun defaultViewModel(color: Int): ResourceViewModel =
        ResourceViewModel(DEFAULT_ICON, R.drawable.ic_account_circle_filled,
            R.string.default_user_icon_description, color)

}