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
package com.android.avatarpicker.tests.unit.viewmodel

import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry
import com.android.avatarpicker.data.ColoredIconsRepository
import com.android.avatarpicker.data.IllustrationsRepository
import com.android.avatarpicker.data.MediaRepository
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCaseImpl
import com.android.avatarpicker.tests.R
import com.android.avatarpicker.ui.ResultHandler
import com.android.avatarpicker.ui.details.DetailsViewModel
import com.android.avatarpicker.ui.details.items.UiState
import com.android.avatatpicker.tests.FakeResultHandler
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {
    private lateinit var resources: Resources
    private lateinit var coloredIconsRepository: ColoredIconsRepository
    private lateinit var mediaRepository: MediaRepository
    private lateinit var handler: ResultHandler

    @Before
    fun setup() {
        resources =
            InstrumentationRegistry.getInstrumentation().getContext().getResources()

        coloredIconsRepository = ColoredIconsRepository(resources)
        mediaRepository = MediaRepository()
        handler = FakeResultHandler()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testNoIllustrations() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            val illustrationsRepository = IllustrationsRepository(resources)
            illustrationsRepository.imagesDescriptionsResId =
                R.array.avatar_image_descriptions_empty
            illustrationsRepository.imagesResId = R.array.avatar_images_empty

            val useCase = GroupedSelectableItemsUseCaseImpl(mediaRepository,
                coloredIconsRepository,
                illustrationsRepository,
                testDispatcher
            )
            val model = DetailsViewModel(useCase, handler)

            Truth.assertThat(model.groups.value.size).isEqualTo(1)
            Truth.assertThat(model.groups.value[0].size).isEqualTo(10)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testIllustrations() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            val illustrationsRepository = IllustrationsRepository(resources)
            illustrationsRepository.imagesDescriptionsResId = R.array.avatar_image_descriptions_png
            illustrationsRepository.imagesResId = R.array.avatar_images_png

            val useCase = GroupedSelectableItemsUseCaseImpl(mediaRepository,
                coloredIconsRepository,
                illustrationsRepository,
                testDispatcher
            )
            val model = DetailsViewModel(useCase, handler)

            Truth.assertThat(model.groups.value.size).isEqualTo(2)
            Truth.assertThat(model.groups.value[0].size).isEqualTo(2)
            Truth.assertThat(model.groups.value[1].size).isEqualTo(3)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testIllustrationSelection() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            val illustrationsRepository = IllustrationsRepository(resources)
            illustrationsRepository.imagesDescriptionsResId = R.array.avatar_image_descriptions_png
            illustrationsRepository.imagesResId = R.array.avatar_images_png

            val useCase = GroupedSelectableItemsUseCaseImpl(mediaRepository,
                coloredIconsRepository,
                illustrationsRepository,
                testDispatcher
            )
            val model = DetailsViewModel(useCase, handler)
            Truth.assertThat(handler.getSelected()).isNull()
            Truth.assertThat(handler.uiState.value is UiState.None).isTrue()
            handler.onSelect(model.groups.value[1][0])
            Truth.assertThat(handler.getSelected()).isNotNull()
            Truth.assertThat(handler.uiState.value is UiState.Success<*>).isTrue()
        } finally {
            Dispatchers.resetMain()
        }
    }
}
