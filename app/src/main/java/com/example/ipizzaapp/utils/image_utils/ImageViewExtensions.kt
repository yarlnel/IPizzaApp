package com.example.ipizzaapp.utils.image_utils

import android.graphics.Bitmap
import android.widget.ImageView

fun ImageView.setFitImage(bitmap: Bitmap) {
    val scaledBitmap =
        Bitmap.createScaledBitmap(bitmap, width, height, true)

    setImageBitmap(scaledBitmap)
}