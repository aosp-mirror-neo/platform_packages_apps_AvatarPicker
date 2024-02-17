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
package com.android.avatarpicker.tests.unit.data


import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry
import com.android.avatarpicker.data.IllustrationsRepository
import com.android.avatarpicker.data.entity.ResourceEntity
import com.android.avatarpicker.tests.R
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IllustrationsRepositoryTest {

    private lateinit var resources: Resources
    private lateinit var illustrationsRepository: IllustrationsRepository

    @Before
    fun setUp() {
        resources =
            InstrumentationRegistry.getInstrumentation().getContext().getResources()
        illustrationsRepository = IllustrationsRepository(resources)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testEmptyArrays() = runTest {
        illustrationsRepository.imagesDescriptionsResId = R.array.avatar_image_descriptions_empty
        illustrationsRepository.imagesResId = R.array.avatar_images_empty
        var items = listOf<ResourceEntity>()
        launch { items = illustrationsRepository.getItems() }
        advanceUntilIdle()
        Truth.assertThat(items.size).isEqualTo(0)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testPngArrays() = runTest {
        illustrationsRepository.imagesDescriptionsResId = R.array.avatar_image_descriptions_png
        illustrationsRepository.imagesResId = R.array.avatar_images_png
        var items = listOf<ResourceEntity>()
        launch { items = illustrationsRepository.getItems() }
        advanceUntilIdle()

        Truth.assertThat(items.size).isEqualTo(3)
        Truth.assertThat(items[0].drawableId).isEqualTo(R.drawable.light_blue)
        Truth.assertThat(items[0].descriptionId).isEqualTo(R.string.light_blue)
        Truth.assertThat(items[0].colorInt).isNull()
        Truth.assertThat(items[1].drawableId).isEqualTo(R.drawable.light_yellow)
        Truth.assertThat(items[1].descriptionId).isEqualTo(R.string.light_yellow)
        Truth.assertThat(items[1].colorInt).isNull()
        Truth.assertThat(items[2].drawableId).isEqualTo(R.drawable.white)
        Truth.assertThat(items[2].descriptionId).isEqualTo(R.string.white)
        Truth.assertThat(items[2].colorInt).isNull()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testEmptyDescriptionArray() = runTest {
        illustrationsRepository.imagesDescriptionsResId = R.array.avatar_image_descriptions_empty
        illustrationsRepository.imagesResId = R.array.avatar_images_png
        var ietms = listOf<ResourceEntity>()
        launch { ietms = illustrationsRepository.getItems() }
        advanceUntilIdle()

        Truth.assertThat(ietms.size).isEqualTo(3)
        Truth.assertThat(ietms[0].drawableId).isEqualTo(R.drawable.light_blue)
        Truth.assertThat(ietms[0].descriptionId).isEqualTo(R.string.default_user_icon_description)
        Truth.assertThat(ietms[0].colorInt).isNull()
        Truth.assertThat(ietms[1].drawableId).isEqualTo(R.drawable.light_yellow)
        Truth.assertThat(ietms[1].descriptionId).isEqualTo(R.string.default_user_icon_description)
        Truth.assertThat(ietms[1].colorInt).isNull()
        Truth.assertThat(ietms[2].drawableId).isEqualTo(R.drawable.white)
        Truth.assertThat(ietms[2].descriptionId).isEqualTo(R.string.default_user_icon_description)
        Truth.assertThat(ietms[2].colorInt).isNull()
    }
}