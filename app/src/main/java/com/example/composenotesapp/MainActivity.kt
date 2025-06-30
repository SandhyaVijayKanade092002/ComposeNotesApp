package com.example.composenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composenotesapp.ui.theme.ComposeNotesAppTheme
import com.example.composenotesapp.view.MainScreen
import com.example.composenotesapp.viewmodel.NoteViewModel
import com.example.composenotesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNotesAppTheme {
                val viewModel: NoteViewModel = viewModel(
                    factory = NoteViewModelFactory(application)
                )
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
