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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.android.avatarpicker.tests.R
import com.android.avatarpicker.ui.info.InfoCard
import com.android.avatarpicker.ui.info.InfoViewModel
import com.android.avatarpicker.ui.theme.AvatarPickerTheme
import org.junit.Rule
import org.junit.Test

class InfoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDefaultIconsNoDescriptionInfoCard() {
        val defaultIconsNoDescriptionUseCase = DefaultIconsFakeUseCase(null)
        val model =
            InfoViewModel(defaultIconsNoDescriptionUseCase,
                R.drawable.ic_account_circle_outline,
                R.string.avatar_picker_title)
        // Start the app
        composeTestRule.setContent {
            AvatarPickerTheme {
                InfoCard(model)
            }
        }
        composeTestRule.onNodeWithText("Choose a picture").assertIsDisplayed()
    }

    @Test
    fun testDefaultIconsDescriptionInfoCard() {
        val useCase = DefaultIconsFakeUseCase("select default icon")
        val model =
            InfoViewModel(useCase,
                R.drawable.ic_account_circle_outline,
                R.string.avatar_picker_title)
        // Start the app
        composeTestRule.setContent {
            AvatarPickerTheme {
                InfoCard(model)
            }
        }
        composeTestRule.onNodeWithText("Choose a picture").assertIsDisplayed()
        composeTestRule.onNodeWithText("select default icon").assertIsDisplayed()
    }
}
