package com.subhajeet.ebookadminapp.viewModel

import androidx.lifecycle.ViewModel
import com.subhajeet.ebookadminapp.data.model.Book
import com.subhajeet.ebookadminapp.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import javax.inject.Inject
@HiltViewModel
class MyViewModel  @Inject constructor(private val repo:Repo) : ViewModel() {

    // 🔢 StateFlows to hold form inputs
    private val _bookName = MutableStateFlow("")
    val bookName: StateFlow<String> = _bookName

    private val _author = MutableStateFlow("")
    val author: StateFlow<String> = _author

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl

    private val _pdfUrl = MutableStateFlow("")
    val pdfUrl: StateFlow<String> = _pdfUrl

    // 🧠 Update functions for each input field
    fun onBookNameChanged(value: String) { _bookName.value = value }
    fun onAuthorChanged(value: String) { _author.value = value }
    fun onCategoryChanged(value: String) { _category.value = value }
    fun onImageUrlChanged(value: String) { _imageUrl.value = value }
    fun onPdfUrlChanged(value: String) { _pdfUrl.value = value }

    // 📤 Upload book to Firebase by calling the repository
    fun uploadBook(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val name = _bookName.value.trim()
        val pdf = _pdfUrl.value.trim()

        // ✅ Validate required fields
        if (name.isEmpty() || pdf.isEmpty()) {
            onFailure("Book name and PDF URL are required.")
            return
        }

        // ✏️ Create a Book object from current state
        val book = Book(
            bookName = name,
            bookAuthor = _author.value,
            bookCategory = _category.value,
            bookImage = _imageUrl.value,
            bookUrl = pdf
        )
        // 🔁 Call repository to upload
        repo.uploadBook(book, onSuccess, onFailure)
    }
    // 🔄 Clear form fields after successful upload
    fun clearForm() {
        _bookName.value = ""
        _author.value = ""
        _category.value = ""
        _imageUrl.value = ""
        _pdfUrl.value = ""
    }
}