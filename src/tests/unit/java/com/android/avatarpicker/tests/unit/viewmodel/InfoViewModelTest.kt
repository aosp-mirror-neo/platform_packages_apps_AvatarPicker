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
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCase
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCaseImpl
import com.android.avatarpicker.tests.R
import com.android.avatarpicker.ui.info.InfoViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class InfoViewModelTest {
    private lateinit var resources: Resources
    private lateinit var illustrationsRepository: IllustrationsRepository
    private lateinit var coloredIconsRepository: ColoredIconsRepository
    private lateinit var mediaRepository: MediaRepository
    private lateinit var useCase: GroupedSelectableItemsUseCase

    @Before
    fun setup() {
        resources =
            InstrumentationRegistry.getInstrumentation().getContext().getResources()
        illustrationsRepository = IllustrationsRepository(resources)
        coloredIconsRepository = ColoredIconsRepository(resources)
        mediaRepository = MediaRepository()
        useCase =
            GroupedSelectableItemsUseCaseImpl(mediaRepository,
                coloredIconsRepository,
                illustrationsRepository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDescription() = runTest {
        val model =
            InfoViewModel(useCase,
                R.drawable.ic_account_circle_outline,
                R.string.avatar_picker_title)
        advanceUntilIdle()
        assertThat(model.description.value).isNull()
    }
}