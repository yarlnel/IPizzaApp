package com.example.ipizzaapp.utils.image_utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.ipizzaapp.abstractions.BaseRxAndLoggingClass
import com.example.ipizzaapp.db.dao.ImageDao
import com.example.ipizzaapp.models.ImageEntity
import com.example.ipizzaapp.utils.handlers.OnBitmapLoaded
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageBitmapLoader
    @Inject constructor(
        private val picasso: Picasso,
        private val imageDao: ImageDao,
    ) : BaseRxAndLoggingClass() {

    fun loadImageBitmap(
        imageUrl: String,
        callbackLambda: (Bitmap) -> Unit
    ) {
        if (imageDao.countImagesByUrl(imageUrl) == 0) {
            picasso.load(imageUrl).into(OnBitmapLoaded { bitmap ->
                callbackLambda(bitmap)
                imageDao.insertImage(
                    ImageEntity(imageUrl = imageUrl, bitmap = bitmap)
                )
            })
        } else {
            imageDao.getImageByUrl(imageUrl = imageUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(ImageEntity::bitmap)
                .subscribe(callbackLambda, ::log)
                .let(compositeDisposable::add)
        }
    }

    fun loadImageInto(imageUrl: String, imageView: ImageView) {
        loadImageBitmap(imageUrl, imageView::setFitImage)
    }

    fun loadAndSetBitmapInto (imageUrl: String, imageView: ImageView) {
        loadImageBitmap(imageUrl) { bitmap ->
            imageView.setFitImage(bitmap)
            compositeDisposable.clear()
        }
    }

}