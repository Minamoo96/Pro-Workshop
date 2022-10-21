package com.fury.labs.professionalworkshop.models

import com.google.firebase.Timestamp

data class Products(
    val image: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val address: String = "",
    val payment: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val timeToDeliver: String = "",
    val documentId: String = ""
)
