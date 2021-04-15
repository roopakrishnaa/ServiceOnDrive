package com.example.labtest1.feeskeeper.serviceondrive.DbConfig


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity(tableName = "userDetailsTables") @Parcelize
class DriverDetails (


    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="DriverId") val DriverId:Int,
    @ColumnInfo(name="FirstName") val FirstName:String,
    @ColumnInfo(name="LastName") val LastNmae:String,
    @ColumnInfo(name="Email") val Email:String,
    @ColumnInfo(name="Password") val Password:String,
    @ColumnInfo(name="UserImg") val UserImg:String,
    @ColumnInfo(name="CurrentLatititue ") var CurrentLatititue:Double,
    @ColumnInfo(name="currentLongitude ") val currentLongitude:Double,
    @ColumnInfo(name="RidersLatititue ") var RidersLatititue:Double,
    @ColumnInfo(name="RiderstLongitude ") val RidersLongitude:Double,
    @ColumnInfo(name="DestinationLatititue ") val DestinationLatititue:Double,
    @ColumnInfo(name="DestinationLongitude ") val DestinationLongitude:Double,
    @ColumnInfo(name="FormattedDestination") val formattedDestination:String,
    @ColumnInfo(name="FormattedCurrentLocation") val FormattedCurrentLocation:String,
    @ColumnInfo(name="rideStatus") val rideStatus:String,
    @ColumnInfo(name="riderborded") val riderborded:Boolean
) : Parcelable

