package com.hjhj.bookapi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize //직렬화!
data class Book(
    @SerializedName("itemId") val id:Long,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String,
    @SerializedName("coverSmallUrl") val coverSmallUrl:String
    ):Parcelable
