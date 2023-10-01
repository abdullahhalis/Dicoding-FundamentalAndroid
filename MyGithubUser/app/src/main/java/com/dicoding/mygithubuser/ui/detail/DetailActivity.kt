package com.dicoding.mygithubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.databinding.ActivityDetailBinding
import com.dicoding.mygithubuser.ui.favorite.FavoriteViewModel
import com.dicoding.mygithubuser.ui.follow.SectionPagerAdapter
import com.dicoding.mygithubuser.utils.ViewModelFactory
import com.dicoding.mygithubuser.utils.loadImage
import com.dicoding.mygithubuser.utils.showLoading
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var factory: ViewModelFactory
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
    private var username: String? = null
    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.detail)
        username = intent.getStringExtra(USERNAME)

        detailViewModel.getDetailUser(username.toString())

        factory = ViewModelFactory.getInstance(this)

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
                sectionPagerAdapter.username = userData.login
                viewPager.adapter = sectionPagerAdapter
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = follow[position]
                }.attach()
            }

            favoriteViewModel.getFavoriteUserByUsername(userData.login).observe(this@DetailActivity) { user ->
                isFavorited = user != null
                setFavorite(isFavorited)

                binding.apply {
                    fabFavorite.setOnClickListener {
                        if (isFavorited) {
                            favoriteViewModel.deleteFavorite(user)
                            isFavorited = false
                        } else {
                            val favoriteUser = FavoriteUser(
                                username = userData.login,
                                avatarUrl = userData.avatarUrl
                            )
                            favoriteViewModel.addFavorite(favoriteUser)
                            isFavorited = true
                        }
                        setFavorite(isFavorited)
                    }
                }
            }
        }

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.showLoading(it)
        }
    }

    private fun setFavorite(isFavorited: Boolean){
        if (isFavorited){
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_favorite))
        }else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_favorite_border))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.menu_share -> {
                val share = Intent(Intent.ACTION_SEND)
                share.putExtra(Intent.EXTRA_TEXT, "Yoo check this Github user: https://github.com/${username}")
                share.type = "text/plain"
                startActivity(Intent.createChooser(share, "Share To:"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "DetailActivity"
        const val USERNAME = "abdullahhalis"
    }
}