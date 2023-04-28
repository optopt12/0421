package com.example.chatbot.placesDetails

import com.example.chatbot.Adapter.NestedData

data class data(
    val photoList: List<NestedData>,
    val formatted_address: String,
    val name: String,
    val formatted_phone_number: String
)


