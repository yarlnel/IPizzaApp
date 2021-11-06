package com.example.ipizzaapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ipizzaapp.models.ImageEntity
import io.reactivex.Single
import java.util.*

@Dao
interface ImageDao {
   @Query("SELECT * FROM image_table")
   fun getAllImages () : Single<List<ImageEntity>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertImage (imageEntity: ImageEntity)

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertAllImages(listOfImages: List<ImageEntity>)

   @Query("SELECT * FROM image_table WHERE image_url = :imageUrl LIMIT 1")
   fun getImageByUrl (imageUrl: String) : Single<ImageEntity>

   @Query("SELECT COUNT(*) FROM image_table WHERE image_url = :imageUrl")
   fun countImagesByUrl(imageUrl: String) : Int
}