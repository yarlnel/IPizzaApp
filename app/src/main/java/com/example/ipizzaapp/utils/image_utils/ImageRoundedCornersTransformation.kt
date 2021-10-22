package com.example.ipizzaapp.utils.image_utils

import android.graphics.*
import com.squareup.picasso.Transformation
import android.graphics.RectF

import android.graphics.Bitmap

import android.graphics.Shader

import android.graphics.BitmapShader




// I get code from https://gist.github.com/aprock/6213395 :)

class ImageRoundedCornersTransformation(private val radius : Int) : Transformation {
    private val margin = 0


    override fun transform(source: Bitmap): Bitmap? {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawRoundRect(
            RectF(
                margin.toFloat(),
                margin.toFloat(),
                (source.width - margin).toFloat(),
                (source.height - margin).toFloat()
            ), radius.toFloat(), radius.toFloat(), paint
        )
        if (source != output) {
            source.recycle()
        }
        return output
    }
    override fun key(): String {
        return "rounded_corners"
    }
}