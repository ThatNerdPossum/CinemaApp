package com.example.mycinema.data.model

import java.util.Date

data class Movie(
    val id: String,
    var name: String,
    var resume: String,
    var startDate: String,
    var duration: String,
    var genre: String,
    var imageURL: String
)