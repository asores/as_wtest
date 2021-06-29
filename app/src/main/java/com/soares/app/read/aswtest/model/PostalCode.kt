package com.soares.app.read.aswtest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "postal_code", indices = [Index(value = ["postal_code"], unique = true)])
class PostalCode(
    @field:ColumnInfo(name = "postal_code")
    var postalCode: String,

    @field:ColumnInfo(name = "desc")
    var desc: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}