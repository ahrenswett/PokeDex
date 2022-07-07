package com.ahrenswett.samaritanpokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ahrenswett.samaritanpokedex.navigation.Navigation
import com.ahrenswett.samaritanpokedex.ui.theme.SamaritanPokedexTheme
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamaritanPokedexTheme {
                Navigation()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SamaritanPokedexTheme {
        Navigation()
    }
}