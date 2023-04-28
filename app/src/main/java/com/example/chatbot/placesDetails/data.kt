package com.example.chatbot.placesDetails

import android.net.Uri
import com.example.chatbot.Adapter.NestedData
import retrofit2.http.Url

data class data(
//    val photoList: List<NestedData>,
    val image: String,
    val formatted_address: String,
    val name: String,
    val formatted_phone_number: String
)


