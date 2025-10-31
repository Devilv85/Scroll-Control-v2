package com.vishal.scrollcontrol.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vishal.scrollcontrol.ui.theme.ScrollControlTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for Scroll Control v2
 * Entry point for the minimalist digital wellness app
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            ScrollControlTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Add main app content
                    // This will be the minimalist 3-screen navigation
                }
            }
        }
    }
}