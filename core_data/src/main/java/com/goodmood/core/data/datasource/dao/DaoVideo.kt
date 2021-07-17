package com.goodmood.core.data.datasource.dao

import androidx.room.*
import com.goodmood.core.data.datasource.entities.VideoEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class DaoVideo {

    @Query("select * from VideoEntity order by timestamp desc")
    abstract fun get(): Observable<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entityVideo: VideoEntity): Completable

    @Delete
    abstract fun delete(entityVideo: VideoEntity): Completable
}