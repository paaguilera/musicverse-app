package com.example.musicverse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicverse.dao.UsuarioDAO
import com.example.musicverse.model.Usuario

@Database(
    entities = [Usuario::class],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDAO(): UsuarioDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "us_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}