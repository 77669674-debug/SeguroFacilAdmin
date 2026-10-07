package com.example.segurofaciladmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.segurofaciladmin.ui.navigation.AppNavigation
import com.example.segurofaciladmin.ui.theme.SegurofaciladminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SegurofaciladminTheme {
                AppNavigation()
            }
        }
    }
}