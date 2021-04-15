package com.example.labtest1.feeskeeper.serviceondrive.DbConfig

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.LiveData

class driverDetailsRepo ( private val driverDetailsDao: driverDetailsDao ) {




    suspend fun insert(driver : DriverDetails) {

        driverDetailsDao.insert(driver)
    }

    suspend fun update(driver : DriverDetails) {

        driverDetailsDao.update(driver)

    }


    val allData: LiveData<List<DriverDetails>> = driverDetailsDao.getalldata()

}