package com.example.labtest1.feeskeeper.serviceondrive.DbConfig
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface driverDetailsDao {



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insert(user: DriverDetails )


    @Query("SELECT * from userDetailsTables")
    fun getalldata(): LiveData<List<DriverDetails>>

    @Update
    suspend fun update(user : DriverDetails)

    //Delete all data
    @Query("DELETE FROM userDetailsTables")
    suspend fun deleteAll()



}