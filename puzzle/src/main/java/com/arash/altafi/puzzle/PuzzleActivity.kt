package com.arash.altafi.puzzle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.arash.altafi.puzzle.databinding.ActivityPuzzleBinding

class PuzzleActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPuzzleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(binding.root)
    }

}