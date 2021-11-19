package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.models.LocalImageEntity
import io.reactivex.Single

@Dao
interface ImageDao {
   @Query("SELECT * FROM image_table")
   fun getAllImages () : Single<List<LocalImageEntity>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertImage (imageEntity: LocalImageEntity)

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertAllImages(listOfImages: List<LocalImageEntity>)

   @Query("SELECT * FROM image_table WHERE image_url = :imageUrl LIMIT 1")
   fun getImageByUrl (imageUrl: String) : Single<LocalImageEntity>

   @Query("SELECT COUNT(*) FROM image_table WHERE image_url = :imageUrl")
   fun countImagesByUrl(imageUrl: String) : Int
}