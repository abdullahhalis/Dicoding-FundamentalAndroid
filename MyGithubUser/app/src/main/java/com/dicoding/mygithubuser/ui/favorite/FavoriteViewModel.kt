package com.dicoding.mygithubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.data.FavoriteUserRepository
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser

class FavoriteViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = favoriteUserRepository.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String) : LiveData<FavoriteUser> = favoriteUserRepository.getFavoriteUserByUsername(username)

    fun addFavorite(favoriteUser: FavoriteUser) {
        favoriteUserRepository.addFavorite(favoriteUser)
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        favoriteUserRepository.deleteFavorite(favoriteUser)
    }
}