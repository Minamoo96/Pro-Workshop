package com.fury.labs.professionalworkshop.models

import com.google.firebase.Timestamp

data class Orders(
    val userId: String = "",
    val username: String = "",
    val phone: String = "",
    val documentId: String = "",
    val image: Int = 0,
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp,
    val address: String = "",
    val payment: String = ""
)
