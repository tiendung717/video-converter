package com.alticode.core.data.datasource.dao

import androidx.room.*
import com.alticode.core.data.datasource.entities.VideoEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class DaoVideo {

    @Query("select * from VideoEntity order by timestamp desc")
    abstract suspend fun get(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entityVideo: VideoEntity)

    @Delete
    abstract suspend fun delete(entityVideo: VideoEntity)
}