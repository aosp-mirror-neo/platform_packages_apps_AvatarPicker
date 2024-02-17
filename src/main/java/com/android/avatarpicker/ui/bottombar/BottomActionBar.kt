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

package com.android.avatarpicker.ui.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.avatarpicker.R

@Composable
fun BottomActionBar(
    doneEndabled: Boolean, onSuccess: () -> Unit, onFail: () -> Unit
) {

    Column(Modifier.fillMaxWidth().fillMaxHeight()) {
        Divider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            startIndent = 0.dp
        )
        Row(
            Modifier.fillMaxWidth().fillMaxHeight().padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp, Alignment.End
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = onFail, colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    disabledContentColor = MaterialTheme.colorScheme.primary.copy(.12f)
                )
            ) {
                Text(text = stringResource(R.string.cancel))
            }

            Button(
                onClick = {
                    onSuccess()
                }, colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surfaceContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(.12f),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ), enabled = doneEndabled
            ) {
                Text(text = stringResource(R.string.done))
            }
        }
    }
}