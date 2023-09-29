package com.dicoding.mygithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.response.ItemsItem
import com.dicoding.mygithubuser.databinding.ActivityMainBinding
import com.dicoding.mygithubuser.ui.list.ListAdapter
import com.dicoding.mygithubuser.ui.list.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listViewModel by viewModels<ListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        listViewModel.isLoading.observe(this) {
            binding.progressBar.showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    listViewModel.getUsers(searchView.text.toString())
                    false
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setListUser(itemsItem: List<ItemsItem>) {
        val listUserAdapter = ListAdapter(itemsItem)
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = listUserAdapter
        }
    }
}