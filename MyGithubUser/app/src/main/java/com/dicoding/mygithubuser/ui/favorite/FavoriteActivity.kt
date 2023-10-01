package com.dicoding.mygithubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubuser.ui.list.ListAdapter
import com.dicoding.mygithubuser.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.favorite)
        factory = ViewModelFactory.getInstance(this)

        favoriteViewModel.getAllFavoriteUsers().observe(this@FavoriteActivity){
            val listUserAdapter = ListAdapter(it)
            binding.apply {
                rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                rvFavorite.adapter = listUserAdapter
            }
        }
    }
}