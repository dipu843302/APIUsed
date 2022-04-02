package com.example.apiused.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseClass(
    val id:String,val title:String,val firstName:String,
    val lastName:String,val picture:String
):Parcelable
