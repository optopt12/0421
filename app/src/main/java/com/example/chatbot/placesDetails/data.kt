package com.example.chatbot.placesDetails

import android.net.Uri
import android.os.Parcelable
import com.example.chatbot.Adapter.NestedData
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url

@Parcelize
data class data(
//    val photoList: List<NestedData>,
    var image: String = "",
    var formatted_address: String = "---",
    var name: String = "not found",
    var formatted_phone_number: String = "---"
) : Parcelable


