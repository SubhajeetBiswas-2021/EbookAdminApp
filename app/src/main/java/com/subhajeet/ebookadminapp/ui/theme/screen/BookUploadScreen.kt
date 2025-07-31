package com.subhajeet.ebookadminapp.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.subhajeet.ebookadminapp.viewModel.MyViewModel

@Composable
fun BookUploadScreen(
    viewModel: MyViewModel? = hiltViewModel()  // ViewModel injected by Hilt
) {
    val context = LocalContext.current

    // 🧠 Collect input states from ViewModel
    val bookName by viewModel?.bookName?.collectAsState() ?: remember { mutableStateOf("") } //We're collecting states from a MyViewModel using collectAsState() to bind UI inputs.
    val author by viewModel?.author?.collectAsState() ?: remember { mutableStateOf("") }
    val category by viewModel?.category?.collectAsState() ?: remember { mutableStateOf("") }
    val imageUrl by viewModel?.imageUrl?.collectAsState() ?: remember { mutableStateOf("") }
    val pdfUrl by viewModel?.pdfUrl?.collectAsState() ?: remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(55.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 🔽 Input Fields
        OutlinedTextField(
            value = bookName,
            onValueChange = { viewModel?.onBookNameChanged(it) },
            label = { Text("Book Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = author,
            onValueChange = { viewModel?.onAuthorChanged(it) },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { viewModel?.onCategoryChanged(it) },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { viewModel?.onImageUrlChanged(it) },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pdfUrl,
            onValueChange = { viewModel?.onPdfUrlChanged(it) },
            label = { Text("PDF URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 📤 Upload Button
        Button(
            onClick = {
                viewModel?.uploadBook(
                    onSuccess = {
                        Toast.makeText(context, "Book uploaded successfully!", Toast.LENGTH_SHORT).show()
                        viewModel.clearForm()
                    },
                    onFailure = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Book")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👁️ Live Preview Card
        if (bookName.isNotEmpty() || author.isNotEmpty()) {
            Text("📘 Book Preview", style = MaterialTheme.typography.titleMedium)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Title: $bookName", style = MaterialTheme.typography.bodyLarge)
                    Text("Author: $author", style = MaterialTheme.typography.bodyMedium)
                    Text("Category: $category", style = MaterialTheme.typography.bodyMedium)

                    if (imageUrl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),    //rememberAsyncImagePainter(url)This loads an image from the internet (a URL).
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "PDF URL: $pdfUrl",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookUploadScreenPreview() {
    BookUploadScreen(viewModel = null)
}