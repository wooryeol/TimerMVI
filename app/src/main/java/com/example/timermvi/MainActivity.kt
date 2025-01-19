package com.example.timermvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timermvi.ui.theme.TimerMVITheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerMVITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimerMVITheme {
        Greeting("Android")
    }
}

@Composable
fun TimerScreen(modifier: Modifier = Modifier, viewModel: TimerViewModel = viewModel()){
    val state by viewModel.data.collectAsState()

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "${state.hour.toString().padStart(2,'0')}:${state.time.toString().padStart(2,'0')}.${state.seconds.toString().padStart(2,'0')}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Button(onClick = {
                    if (state.isRunning) {
                        viewModel.onIntent(TimerIntent.Stop)
                    } else {
                        viewModel.onIntent(TimerIntent.Start)
                    }
                },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(text = if(state.isRunning) "Stop" else "Start")
                }

                Button(
                    onClick = {viewModel.onIntent(TimerIntent.Reset)}
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}