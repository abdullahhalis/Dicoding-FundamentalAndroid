package com.dicoding.mygithubuser.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithubuser.data.FavoriteUserRepository
import com.dicoding.mygithubuser.di.Injection
import com.dicoding.mygithubuser.ui.favorite.FavoriteViewModel
import com.dicoding.mygithubuser.ui.list.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val favoriteUserRepository: FavoriteUserRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favoriteUserRepository) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(favoriteUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}