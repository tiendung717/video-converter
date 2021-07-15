package com.goodmood.core.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.goodmood.core.data.datasource.dao.DaoVideo
import com.goodmood.core.data.datasource.entities.VideoEntity


@Database(
    entities = [
        VideoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VideoDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "goodmood-editor"

        fun getDatabase(context: Context): VideoDatabase {
            val builder = Room.databaseBuilder(context, VideoDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
            return builder.build()
        }
    }

    abstract fun daoVideo(): DaoVideo
}