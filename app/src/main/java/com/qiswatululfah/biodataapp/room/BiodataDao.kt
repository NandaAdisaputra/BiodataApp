package com.qiswatululfah.biodataapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface BiodataDao {
    @Query("SELECT * from biodata")
    fun getAllBiodata(): List<BiodataModel>

    @Insert(onConflict = REPLACE)
    fun insertBiodata(biodata: BiodataModel)

    @Delete
    fun deleteBiodata(biodata: BiodataModel)

    @Query("UPDATE biodata SET number =:number, name=:name, birth=:birth, gender=:gender, address=:address WHERE id=:id")
    fun updateBiodata(
        number: String,
        name: String,
        birth: String,
        gender: String,
        address: String,
        id: Long
    )
}