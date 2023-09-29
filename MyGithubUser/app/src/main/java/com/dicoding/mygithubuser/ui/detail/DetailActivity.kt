package com.dicoding.mygithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.databinding.ActivityDetailBinding
import com.dicoding.mygithubuser.ui.follow.SectionPagerAdapter
import com.dicoding.mygithubuser.ui.loadImage
import com.dicoding.mygithubuser.ui.showLoading
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.detail)
        val username = intent.getStringExtra(USERNAME)

        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userData.observe(this) { userData ->
            binding.apply {
                imageView.loadImage(userData.avatarUrl)
                tvUsername.text = userData.login
                tvName.text = userData.name

                val follow = mutableListOf(
                    String.format(getString(R.string.followers), userData.followers),
                    String.format(getString(R.string.following), userData.following)
                )

                val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
                sectionPagerAdapter.username = username.toString()
                viewPager.adapter = sectionPagerAdapter
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = follow[position]
                }.attach()
            }
        }

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.showLoading(it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val TAG = "DetailActivity"
        const val USERNAME = "abdullahhalis"
    }
}