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

package com.android.avatarpicker.data

import android.content.res.Resources
import com.android.avatarpicker.R
import com.android.avatarpicker.data.entity.ResourceEntity
import com.android.internal.util.UserIcons

class ColoredIconsRepository(private val resources: Resources) {
    suspend fun getItems(): List<ResourceEntity> =
        UserIcons.getUserIconColors(resources).map { color ->
            ResourceEntity(
                R.drawable.ic_account_circle_filled, R.string.default_user_icon_description, color
            )
        }
}