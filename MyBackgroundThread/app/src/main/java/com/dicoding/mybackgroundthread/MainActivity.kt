package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Default) {
                    for (i in 0..10) {
                        delay(500)
                        val percentage = i * 10
                        withContext(Dispatchers.Main) {
                            if (percentage == 100) {
                                tvStatus.setText(R.string.task_completed)
                            } else {
                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }
                }
            }
        }
    }
}
