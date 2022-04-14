package com.example.apiused.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseClass(
    val id: String? = null, val title: String? = null, val firstName: String? = null,
    val lastName: String? = null, val picture: String? = null
) : Parcelable
