package com.example.flowsadvanced

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.flowsadvanced.ui.theme.FlowsAdvancedTheme

class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowsAdvancedTheme {
                val time = viewModel.countdownFlow.collectAsState(initial = 10)
                // A surface container using the 'background' color from the theme
               Box(modifier = Modifier.fillMaxSize()) {
                   Text(
                       text = time.value.toString(),
                       fontSize= 30.sp,
                       modifier = Modifier.align(Alignment.Center)
                   )
               }
                if(time.value == 0) {
                    Toast.makeText(this, "Finished Timer", Toast.LENGTH_SHORT).show()
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
    FlowsAdvancedTheme {
        Greeting("Android")
    }
}