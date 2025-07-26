package com.subhajeet.ebookadminapp.data.model

// Data class representing a Book entity in Firebase
data class Book(
    val bookName: String ?= "",
    val bookAuthor: String ?= "",
    val bookCategory: String ?= "",
    val bookImage: String ?= "",
    val bookUrl: String ?= ""
)