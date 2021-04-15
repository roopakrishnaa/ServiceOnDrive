package com.lambtonserviceon.models.directions


import com.google.gson.annotations.SerializedName

data class EndLocation(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)