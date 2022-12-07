package com.ansori.moviekuwebinarseries.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ansori.moviekuwebinarseries.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this,MovieList::class.java))
            finish()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}