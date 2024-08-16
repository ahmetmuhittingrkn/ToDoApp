package com.ahmetmuhittingurkan.todoapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmetmuhittingurkan.todoapp.entity.Notlar

@Database(entities = [Notlar::class], version = 2)
abstract class Veritabani: RoomDatabase() {

    abstract fun getNotlarDao():NotlarDao

    companion object {
        @Volatile
        private var INSTANCE: Veritabani? = null

        fun getDatabase(context: Context): Veritabani {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Veritabani::class.java,
                    "notlar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}