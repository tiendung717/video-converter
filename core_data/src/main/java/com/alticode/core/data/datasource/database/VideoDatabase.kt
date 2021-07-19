package com.alticode.core.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alticode.core.data.datasource.dao.DaoVideo
import com.alticode.core.data.datasource.entities.VideoEntity


@Database(
    entities = [
        VideoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VideoDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "goodmood-db"

        fun getDatabase(context: Context): VideoDatabase {
            val builder = Room.databaseBuilder(context, VideoDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
            return builder.build()
        }
    }

    abstract fun daoVideo(): DaoVideo
}