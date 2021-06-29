package com.soares.app.read.aswtest

import android.app.Application
import com.soares.app.read.aswtest.data_room.PostalCodeRepository
import com.soares.app.read.aswtest.data_room.PostalCodeRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Application : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)

    private val database by lazy { PostalCodeRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PostalCodeRepository(database.postalCodeDao()) }
}