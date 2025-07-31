package com.subhajeet.ebookadminapp.repo

import com.google.firebase.database.FirebaseDatabase
import com.subhajeet.ebookadminapp.data.model.Book
import javax.inject.Inject

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {


    // 🔗 Create a reference to the "Books" node in your Firebase Realtime Database
    private val dbRef = firebaseDatabase.getReference("Books")

    fun uploadBook(
        book: Book,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        // 🔑 Generate a unique key (ID) for the new book entry
        val bookId = dbRef.push().key

        // ❌ If the key couldn't be generated, return error
        if (bookId == null) {

            onFailure("Failed to generate book ID")
            return
        }

        // 🔼 Upload the book data to Firebase under the generated ID
        dbRef.child(bookId).setValue(book)
            .addOnSuccessListener {
                // ✅ Data uploaded successfully
                onSuccess()
            }
            .addOnFailureListener { e ->
                // ❌ Something went wrong; pass the error message back
                onFailure(e.message ?: "Unknown error occurred")
            }
    }
}