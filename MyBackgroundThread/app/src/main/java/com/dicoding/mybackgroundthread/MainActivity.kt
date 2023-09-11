package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.mybackgroundthread.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        binding. apply {
            btnStart.setOnClickListener{
                executor.execute{
                    try {
                        for(i in 0..10) {
                            Thread.sleep(500)
                            val percentage = i * 10
                            handler.post {
                                if (percentage == 100) {
                                    tvStatus.setText(R.string.task_completed)
                                }else {
                                    tvStatus.text = String.format(getString(R.string.compressing), percentage)
                                }
                            }
                        }
                    }catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}