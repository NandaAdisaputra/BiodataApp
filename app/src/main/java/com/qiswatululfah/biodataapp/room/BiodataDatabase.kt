package com.qiswatululfah.biodataapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BiodataModel::class], version = 1)
abstract class BiodataDatabase : RoomDatabase(){

    abstract fun biodataDao() : BiodataDao

    companion object{
        private var INSTANCE : BiodataDatabase? = null

        fun getInstance(context: Context) : BiodataDatabase?{
            if (INSTANCE == null){
                synchronized(BiodataDatabase::class){
                    INSTANCE = Room.databaseBuilder(context, BiodataDatabase::class.java, "Biodata.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}