package com.example.data.converters

import com.example.data.db.converters.BitmapToStringConverter
import com.example.domain.models.ImageEntity
import com.example.data.models.LocalImageEntity
import javax.inject.Inject

class LocalImageEntityConverter
@Inject constructor(
    private val bitmapToStringConverter: BitmapToStringConverter
) : LocalToDomainConverter<LocalImageEntity, ImageEntity>{
    override fun fromLocal(localModel: LocalImageEntity): ImageEntity {
        return ImageEntity(
            imageUrl = localModel.imageUrl,
            bitmapString = bitmapToStringConverter.bitmapToString(
                localModel.bitmap
            ),
        )
    }

    override fun toLocal(domainModel: ImageEntity): LocalImageEntity {
        return LocalImageEntity(
            imageUrl = domainModel.imageUrl,
            bitmap = bitmapToStringConverter.encodeStringToBitmap(
                domainModel.bitmapString
            ),
        )
    }
}