package com.soares.app.read.aswtest.data_room

import androidx.annotation.WorkerThread
import com.soares.app.read.aswtest.model.PostalCode
import kotlinx.coroutines.flow.Flow


class PostalCodeRepository(private val postalCodeDao: PostalCodeDao) {
    fun listInitialPostalCode(): Flow<List<PostalCode>> = postalCodeDao.getAlphabetizedPostalCode()
    fun rangePostalCode(min: Int, max: Int): List<PostalCode> =
        postalCodeDao.getListPostalCodeRange(min, max)

    var lastPostalCode: PostalCode = postalCodeDao.getLastPostalCode()
    fun listPostalCodeFilter(postalCodeFilter: String, descFilter: String): List<PostalCode> =
        postalCodeDao.getListPostalCodeFilter(postalCodeFilter, descFilter)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(postalCode: PostalCode) {
        postalCodeDao.insert(postalCode)
    }
}