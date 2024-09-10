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

package com.android.avatarpicker.domain

import android.multiuser.Flags
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.foundation.clickable

fun Modifier.applyReadBackOrder() =
    if (Flags.fixAvatarPickerReadBackOrder()) this.semantics { isTraversalGroup = true } else this

fun Modifier.applySelectable(isSelected: Boolean, select:() ->Unit): Modifier {
    if (Flags.fixAvatarPickerSelectedReadBack()) {
        if (isSelected)
            return this.semantics {
                selected = isSelected
                role = Role.Button
            }
        else
            return this.semantics{}.clickable{select()}
    }
    return this.clickable{select()}
}