package com.dicoding.mygithubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.databinding.ActivityMainBinding
import com.dicoding.mygithubuser.ui.favorite.FavoriteActivity
import com.dicoding.mygithubuser.ui.list.ListAdapter
import com.dicoding.mygithubuser.ui.list.MainViewModel
import com.dicoding.mygithubuser.utils.ViewModelFactory
import com.dicoding.mygithubuser.utils.showLoading

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels{ factory }
    private var isChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this@MainActivity)

        mainViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.getUsers(searchView.text.toString())
                    false
                }
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isChecked = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu?.findItem(R.id.menu_theme)
        if (isChecked) {
            menuItem?.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_light))
        }else {
            menuItem?.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_dark))
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_theme -> {
                isChecked = !isChecked
                mainViewModel.saveThemeSetting(isChecked)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListUser(itemsItem: List<ItemsItem>) {
        val listUserAdapter = ListAdapter(itemsItem)
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = listUserAdapter
        }
    }
}