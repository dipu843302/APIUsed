package com.example.apiused.models

data class UserModel(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String,
    val gender: String,
    val email: String,
    val dateOfBirth: String,
    val phone: String,
    val location: Location
)

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val timezone: String
)
