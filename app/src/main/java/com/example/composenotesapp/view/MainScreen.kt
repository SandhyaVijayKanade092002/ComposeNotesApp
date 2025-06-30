package com.example.composenotesapp.view

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.composenotesapp.viewmodel.NoteViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenotesapp.model.NoteEntity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalFocusManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: NoteViewModel){
    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    val notes by viewModel.notes.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Compose Notes App") })
        },
        content = {padding ->
            Column (

                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ){
                OutlinedTextField(value = title,
                    onValueChange = {title = it},
                    label = {Text ("Title")},
                    modifier = Modifier.fillMaxWidth()  // ✅ Correct
                    )
                OutlinedTextField(value = content,
                    onValueChange = {content = it},
                    label={Text("Content")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                    )
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            viewModel.addNote(title, content)
                            title = ""
                            content = ""
                        }
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ){
                    Text("Add Note")
                }
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(notes) { note ->
                        NoteCard(note = note, onDelete = {
                            viewModel.deleteNote(note)
                        })
                    }
                }

            }

        }
    )
}
@Composable
fun NoteCard(note: NoteEntity, onDelete:() -> Unit){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.title, style = MaterialTheme.typography.titleMedium)
            Text(note.content, style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = onDelete,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(androidx.compose.ui.Alignment.End)
            ) {
                Text("Delete")
            }
        }
    }
}