package com.dicoding.mygithubuser.di

import android.content.Context
import com.dicoding.mygithubuser.data.FavoriteUserRepository
import com.dicoding.mygithubuser.data.local.room.FavoriteUserDatabase
import com.dicoding.mygithubuser.utils.AppExecutors
import com.dicoding.mygithubuser.utils.SettingPreferences
import com.dicoding.mygithubuser.utils.dataStore

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        val settingPreferences = SettingPreferences.getInstance(context.dataStore)
        return FavoriteUserRepository.getInstance(dao, appExecutors, settingPreferences)
    }
}