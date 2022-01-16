package com.example.discoveryincubatorpart2.models

data class Stock(
    val id: Int,
    val condition: String,
    val availableQuantity: Int,
    val price: Int
)