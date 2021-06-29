package com.soares.app.read.aswtest.data_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soares.app.read.aswtest.model.PostalCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PostalCode::class], version = 1, exportSchema = false)
abstract class PostalCodeRoomDatabase : RoomDatabase() {

    abstract fun postalCodeDao(): PostalCodeDao

    companion object {
        @Volatile
        private var INSTANCE: PostalCodeRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PostalCodeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostalCodeRoomDatabase::class.java,
                    "as_wtest_database"
                )
                    .allowMainThreadQueries()
                    .addCallback(ASWTestDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ASWTestDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch(Dispatchers.IO) {
                    print("Database criado com sucesso")
                }
            }
        }
    }
}


