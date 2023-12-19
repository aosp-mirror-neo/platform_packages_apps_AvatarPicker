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

package com.android.avatarpicker.domain

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.android.avatarpicker.R
import com.android.avatarpicker.data.entity.ResourceEntity
import com.android.avatarpicker.ui.details.items.ResourceViewModel
import com.android.avatarpicker.ui.details.items.UriTypedItem
import java.io.File
import java.io.IOException


private const val CROP_ACTION = "com.android.camera.action.CROP"
private const val FILE_PROVIDER = ".tempprovider"
private const val DEFAULT_ICON_TINT_COLOR = "default_icon_tint_color"

// Item types definition
const val CAMERA = 1
const val PHOTO_PICKER = 2
const val DEFAULT_ICON = 3
const val ILLUSTRATION = 4
fun ResourceEntity.toViewModel(entityType: Int) =
    ResourceViewModel(entityType, drawableId, descriptionId, colorInt)

fun Context.getTempPNG(name: String): File {
    val imagesDir = File(filesDir, getString(R.string.result_file_name))
    if (!imagesDir.exists()) imagesDir.mkdir()
    return File(imagesDir, "${name}.png")
}

@Throws(IOException::class)
fun File.saveBitmap(bmp: Bitmap) = outputStream().use { stream ->
    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()
}

fun Context.getFileUri(file: File): Uri {
    val authority = applicationContext.packageName + FILE_PROVIDER
    return FileProvider.getUriForFile(applicationContext, authority, file)
}

fun getCropIntent(contentUri: Uri, sizeInPixels: Int) = Intent(CROP_ACTION).apply {
    setDataAndType(contentUri, "image/*")
    addFlags(
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
    putExtra(MediaStore.EXTRA_OUTPUT, contentUri)

    putExtra("crop", true)
    putExtra("scale", true)
    putExtra("scaleUpIfNeeded", true)
    putExtra("aspectX", 1)
    putExtra("aspectY", 1)
    putExtra("outputX", sizeInPixels)
    putExtra("outputY", sizeInPixels)
}

fun ResourceViewModel.toIntent(context: Context): Intent {

    val intent = Intent()
    when (typeId) {
        DEFAULT_ICON -> this.color?.let { intent.putExtra(DEFAULT_ICON_TINT_COLOR, it) }
        ILLUSTRATION -> context.apply {
            BitmapFactory.decodeResource(resources, drawableId)?.let { bitmap ->
                val resultFile = getTempPNG(getString(R.string.result_file_name))
                val contentUri = getFileUri(resultFile)
                resultFile.saveBitmap(bitmap)
                intent.setData(contentUri)
            }
        }
    }
    return intent
}

fun UriTypedItem.toIntent() = Intent().apply { data = uri }
