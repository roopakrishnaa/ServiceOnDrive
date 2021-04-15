package com.example.labtest1.feeskeeper.serviceondrive.DbConfig

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class driverDetailsViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: driverDetailsRepo


    val alldata: LiveData<List<DriverDetails>>


    init {



        val driverDao =  driverDetailsDatabase.getDatabase(application, viewModelScope).driverDetailsDao()

        repository = driverDetailsRepo(driverDao)

        alldata = repository.allData


    }


    fun insert(driver: DriverDetails) = viewModelScope.launch {

        repository.insert(driver)
    }



    fun update(driver: DriverDetails) = viewModelScope.launch {

        repository.update(driver)
    }




}