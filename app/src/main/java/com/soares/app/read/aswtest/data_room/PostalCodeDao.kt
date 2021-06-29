package com.soares.app.read.aswtest.data_room


import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soares.app.read.aswtest.model.PostalCode
import kotlinx.coroutines.flow.Flow


@Dao
interface PostalCodeDao {
    @Query("SELECT * FROM postal_code WHERE id BETWEEN 1 AND 100 ORDER BY id ASC")
    fun getAlphabetizedPostalCode(): Flow<List<PostalCode>>

    @Query("SELECT * FROM postal_code ORDER BY id DESC LIMIT 1")
    fun getLastPostalCode(): PostalCode

    @Query("SELECT * FROM postal_code WHERE id BETWEEN :min AND :max ORDER BY id ASC")
    fun getListPostalCodeRange(@Nullable min:Int, @Nullable max:Int): List<PostalCode>

    @Query("SELECT * FROM postal_code WHERE postal_code LIKE :postalCodeFilter OR `desc` LIKE :descFilter ORDER BY id ASC")
    fun getListPostalCodeFilter(@Nullable postalCodeFilter: String, @Nullable descFilter: String): List<PostalCode>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postalCode: PostalCode)

    @Query("DELETE FROM postal_code")
    fun deleteAll()
}