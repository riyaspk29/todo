package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val toDoDatabaseDao: ToDoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            ToDoDatabase::class.java,
                            "todo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
