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
import androidx.annotation.ArrayRes
import androidx.core.content.res.getResourceIdOrThrow
import com.android.avatarpicker.R
import com.android.avatarpicker.data.entity.ResourceEntity
import com.google.common.annotations.VisibleForTesting

class IllustrationsRepository(private val resources: Resources) {
    @VisibleForTesting
    @ArrayRes
    var imagesResId: Int = R.array.avatar_images

    @VisibleForTesting
    @ArrayRes
    var imagesDescriptionsResId: Int = R.array.avatar_image_descriptions

    @Throws(IllegalArgumentException::class)
    suspend fun getItems(): List<ResourceEntity> {
        val items = mutableListOf<ResourceEntity>()
        val avatarImages = resources.safeObtainTypedArray(imagesResId)?.also { images ->
            val avatarImageDescriptions =
                resources.safeObtainTypedArray(imagesDescriptionsResId)
            for (i in 0 until images.length())
                items.add(
                    ResourceEntity(
                        images.getResourceIdOrThrow(i),
                        avatarImageDescriptions?.safeObtainStringRes(
                            i, R.string.default_user_icon_description
                        ) ?: R.string.default_user_icon_description
                    )
                )
            avatarImageDescriptions?.recycle()
        }
        avatarImages?.recycle()

        return items
    }
}

