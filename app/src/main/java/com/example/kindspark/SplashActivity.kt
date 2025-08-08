package com.example.kindspark

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure the activity is properly initialized before proceeding
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                // Check if activity is still valid before starting MainActivity
                if (!isFinishing && !isDestroyed) {
                    val intent = Intent(this, MainActivity::class.java)
                    // Add flags to ensure proper task management
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                Log.e("SplashActivity", "Error starting MainActivity", e)
                // Fallback: try again with a longer delay
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        if (!isFinishing && !isDestroyed) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                    } catch (ex: Exception) {
                        Log.e("SplashActivity", "Critical error starting MainActivity", ex)
                    }
                }, 1000)
            }
        }, 1500) // Increased delay to 1.5 seconds for better stability
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending callbacks to prevent memory leaks
        Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
    }
}