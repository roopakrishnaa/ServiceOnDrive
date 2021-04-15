package com.lambtonserviceon.models.directions


import com.google.gson.annotations.SerializedName

data class DistanceX(
    @SerializedName("text")
    val text: String,
    @SerializedName("value")
    val value: Int
)