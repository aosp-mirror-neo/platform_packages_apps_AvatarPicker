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

package com.android.avatarpicker.domain

import com.android.avatarpicker.data.ColoredIconsRepository
import com.android.avatarpicker.data.IllustrationsRepository
import com.android.avatarpicker.data.MediaRepository
import com.android.avatarpicker.ui.details.items.SelectableType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroupedSelectableItemsUseCaseImpl(
    private val mediaRepo: MediaRepository,
    private val coloredIconsRepo: ColoredIconsRepository,
    private val illustrationsRepo: IllustrationsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : GroupedSelectableItemsUseCase {
    override suspend operator fun invoke(): List<List<SelectableType>> = withContext(dispatcher) {
        val illustrations =
            illustrationsRepo.getItems().map { entity -> entity.toViewModel(ILLUSTRATION) }
        val media = listOf(
            mediaRepo.getCamera().toViewModel(CAMERA), mediaRepo.getPhotoPicker().toViewModel(
                PHOTO_PICKER
            )
        )
        if (illustrations.isNotEmpty()) {
            return@withContext listOf(media, illustrations)
        } else {
            val icons =
                coloredIconsRepo.getItems().map { entity -> entity.toViewModel(DEFAULT_ICON) }
            return@withContext listOf(media + icons)
        }
    }

    override suspend fun description() = withContext(dispatcher) { null }
}