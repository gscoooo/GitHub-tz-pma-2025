package com.example.myapp006jetpackcomposeminitodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp006jetpackcomposeminitodo.ui.theme.MyApp006JetpackComposeMiniToDoTheme

// You will also need to define this Composable function somewhere in your project.
// For example:
// @Composable
// fun ToDoScreen(modifier: Modifier = Modifier) {
//     Text(text = "My To-Do Screen", modifier = modifier)
// }


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApp006JetpackComposeMiniToDoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoScreen(
                        modifier = Modifier
                            .padding(innerPadding) // systémový padding (status bar apod.)
                            .padding(16.dp) // náš vlastní padding navíc
                    ) // This closing parenthesis was missing
                }
            }
        }
    }
}

