package com.ahmetmuhittingurkan.todoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ahmetmuhittingurkan.todoapp.data.datasource.ToDoDataSource
import com.ahmetmuhittingurkan.todoapp.data.repo.ToDoRepository
import com.ahmetmuhittingurkan.todoapp.di.AppModule
import com.ahmetmuhittingurkan.todoapp.room.NotlarDao
import com.ahmetmuhittingurkan.todoapp.room.Veritabani
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(tds:ToDoDataSource): ToDoRepository{
        return ToDoRepository(tds)
    }

    @Provides
    @Singleton
    fun provideToDoDataSource(ndao:NotlarDao): ToDoDataSource{
        return ToDoDataSource(ndao)
    }

    @Provides
    @Singleton
    fun provideNotlarDao(@ApplicationContext context: Context): NotlarDao {
        val vt = Room.databaseBuilder(context, Veritabani::class.java, "notlar.sqlite")
            .createFromAsset("notlar.sqlite")
            .build()
        return vt.getNotlarDao()
    }


}