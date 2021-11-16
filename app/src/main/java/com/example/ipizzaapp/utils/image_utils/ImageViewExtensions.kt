package com.example.ipizzaapp.utils.image_utils

import android.graphics.Bitmap
import android.widget.ImageView

fun ImageView.setFitImage(bitmap: Bitmap) {
    if (bitmap.width == width && bitmap.height == height)
        setImageBitmap(bitmap)
    else {
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, width, height, true)

        setImageBitmap(scaledBitmap)
    }
}