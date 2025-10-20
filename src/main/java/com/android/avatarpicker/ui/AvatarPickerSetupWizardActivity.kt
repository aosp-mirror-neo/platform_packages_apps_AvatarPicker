/*
 * Copyright (C) 2025 The Android Open Source Project
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

package com.android.avatarpicker.ui

import kotlin.collections.filterNot
import kotlin.collections.map

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.android.avatarpicker.AvatarProviderApp
import com.android.avatarpicker.R
import com.android.avatarpicker.domain.CAMERA
import com.android.avatarpicker.domain.GroupedSelectableItemsUseCase
import com.android.avatarpicker.domain.PHOTO_PICKER
import com.android.avatarpicker.ui.details.DetailsList
import com.android.avatarpicker.ui.details.items.UiState
import com.android.avatarpicker.ui.details.items.SelectableType
import com.google.android.setupcompat.template.FooterBarMixin
import com.google.android.setupcompat.template.FooterButton
import com.google.android.setupcompat.template.FooterButton.ButtonType
import com.google.android.setupdesign.GlifLayout
import com.google.android.setupdesign.util.ThemeHelper
import androidx.compose.foundation.layout.heightIn

class AvatarPickerSetupWizardActivity : AppCompatActivity() {

    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeHelper.getSuwDefaultTheme(this))
        setContentView(R.layout.avatar_setup)

        val layout = findViewById<GlifLayout>(R.id.setup_wizard_layout)
        layout.setIcon(getDrawable(R.drawable.ic_account_suw))
        layout.setHeaderText(R.string.avatar_picker_title)
        layout.setDescriptionText(R.string.avatar_picker_description)

        val mixin: FooterBarMixin = layout.getMixin(FooterBarMixin::class.java)

        val primaryButton: FooterButton = FooterButton.Builder(this)
            .setText(R.string.next)
            .setButtonType(ButtonType.NEXT)
            .setListener(this::onNextButtonClicked)
            .build()

        primaryButton.isEnabled = false
        mixin.setPrimaryButton(primaryButton)

        // Filter PHOTO_PICKER & CAMERA from the getGroupedSelectableItems
        activityViewModel = (application as AvatarProviderApp).let { app ->
            val originalUseCase = app.getGroupedSelectableItems(intent)
            val resultHandler = app.getResultHandler()

            val filteredUseCase = object : GroupedSelectableItemsUseCase {
                override suspend fun invoke(): List<List<SelectableType>> {
                    val originalGroupedItems = originalUseCase()

                    return originalGroupedItems.map { group ->
                        group.filter {
                            item -> (item.typeId != PHOTO_PICKER) && (item.typeId != CAMERA)
                        }
                    }
                }

                override suspend fun description(): String? {
                    return originalUseCase.description()
                }
            }

            ActivityViewModel(filteredUseCase, resultHandler)
        }

        val itemViewComposer = (application as AvatarProviderApp).getItemViewComposer()

        val composeView = findViewById<ComposeView>(R.id.my_compose_view)

        val display = resources.displayMetrics
        val defaultDensity =
            DisplayMetrics.DENSITY_DEVICE_STABLE.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()
        val columns = (4 * defaultDensity / display.density).toInt()

        composeView.setContent {
            val currentResult by activityViewModel.resultHandler.uiState.collectAsState()
            primaryButton.isEnabled = currentResult is UiState.Success<*>

            LazyVerticalGrid(
                modifier = Modifier
                    .heightIn(max = 1500.dp)
                    .padding(top = 0.dp, start = 24.dp, end = 24.dp),
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                DetailsList(activityViewModel.detailsViewModel, itemViewComposer, columns)
            }
        }
    }

    private fun onNextButtonClicked(view: View) {
        setResultAndFinish(activityViewModel.resultHandler)
    }

    private fun setResultAndFinish(resultHandler: ResultHandler) {
        val result = resultHandler.toResultIntent(this)
        resultHandler.unselect()
        setResult(RESULT_OK, result)
        finish()
    }
}