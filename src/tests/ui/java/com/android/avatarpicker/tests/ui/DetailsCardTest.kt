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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.unit.dp
import com.android.avatarpicker.ui.details.DetailsList
import com.android.avatarpicker.ui.details.DetailsViewModel
import com.android.avatarpicker.ui.details.items.ItemViewComposerImpl
import com.android.avatarpicker.ui.theme.AvatarPickerTheme
import com.android.avatatpicker.tests.FakeResultHandler
import org.junit.Rule
import org.junit.Test

class DetailsCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDefaultIconsDetailsCard() {
        val useCase = DefaultIconsFakeUseCase(null)
        val model = DetailsViewModel(useCase, FakeResultHandler())
        // Start the app
        composeTestRule.setContent {
            AvatarPickerTheme {
                LazyVerticalGrid(
                    // Limit width to avoid not rendering last row
                    modifier = Modifier.width(320.dp)
                        .padding(start = 24.dp, end = 24.dp, top = 8.dp),
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    DetailsList(model, ItemViewComposerImpl(), 4)
                }
            }
        }
        composeTestRule.onAllNodesWithContentDescription("Take a photo").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("Choose an image").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("Default user icon").assertCountEquals(8)
    }


    @Test
    fun testIllustrationsDetailsCard() {
        val useCase = IllustrationsFakeUseCase(null)
        val model = DetailsViewModel(useCase, FakeResultHandler())
        // Start the app
        composeTestRule.setContent {
            AvatarPickerTheme {
                LazyVerticalGrid(
                    // Limit width to avoid not rendering last row
                    modifier = Modifier.width(320.dp)
                        .padding(start = 24.dp, end = 24.dp, top = 8.dp),
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    DetailsList(model, ItemViewComposerImpl(), 4)
                }
            }
        }
        composeTestRule.onAllNodesWithContentDescription("Take a photo").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("Choose an image").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("light blue png").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("light yellow png").assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("white png").assertCountEquals(1)
    }
}