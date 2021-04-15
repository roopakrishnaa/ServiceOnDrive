package com.example.labtest1.feeskeeper.serviceondrive.DbConfig

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(DriverDetails::class), version = 5, exportSchema = false)
abstract class driverDetailsDatabase :RoomDatabase () {

    abstract fun driverDetailsDao(): driverDetailsDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch {

                    var user = database.driverDetailsDao()
                    // Delete all content here.
                    // fee.deleteAll()


                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: driverDetailsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): driverDetailsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        driverDetailsDatabase ::class.java,
                        "User_info_database"
                    )

                        .addCallback(
                            WordDatabaseCallback(
                                scope
                            )
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }


    }


}