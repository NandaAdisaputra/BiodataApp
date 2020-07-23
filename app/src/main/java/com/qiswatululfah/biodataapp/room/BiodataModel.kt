package com.qiswatululfah.biodataapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "biodata")
data class BiodataModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "number")
    var number: String? = "",

    @ColumnInfo(name = "name")
    var name: String? = "",

    @ColumnInfo(name = "birth")
    var birth: String? = "",

    @ColumnInfo(name = "gender")
    var gender: String? = "",

    @ColumnInfo(name = "address")
    var address: String? = ""
) : Parcelable