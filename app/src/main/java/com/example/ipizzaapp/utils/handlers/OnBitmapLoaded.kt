package com.example.ipizzaapp.utils.handlers

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class OnBitmapLoaded
    (private val callbackLambda: (Bitmap) -> Unit)
    : Target {
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        bitmap?.let(callbackLambda)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        Log.e("OnBitmapLoaded",
            e?.stackTraceToString() ?: "Exception = null ? wtf ?")
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        TODO("Not yet implemented")
    }
}