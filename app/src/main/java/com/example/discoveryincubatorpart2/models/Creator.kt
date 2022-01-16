package com.example.discoveryincubatorpart2.models

data class Creator(
    val id: Int,
    val name: String,
    val countryOfResidence: String,
    val taxReferenceNumber: String,
    val emailAddress: String
)