package com.dicoding.mygithubuser.data

import androidx.lifecycle.LiveData
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.data.local.room.FavoriteUserDao
import com.dicoding.mygithubuser.utils.AppExecutors
import com.dicoding.mygithubuser.utils.SettingPreferences
import kotlinx.coroutines.flow.Flow

class FavoriteUserRepository(
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors,
    private val settingPreferences: SettingPreferences
) {


    fun addFavorite(favoriteUser: FavoriteUser) {
        appExecutors.diskIo.execute{
            favoriteUserDao.addFavorite(favoriteUser)
        }

    }

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = favoriteUserDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String) : LiveData<FavoriteUser> = favoriteUserDao.getFavoriteUserByUsername(username)

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        appExecutors.diskIo.execute{
            favoriteUserDao.deleteFavorite(favoriteUser)
        }
    }

    fun getThemeSetting(): Flow<Boolean> = settingPreferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) = settingPreferences.saveThemeSetting(isDarkModeActive)

    companion object{
        @Volatile
        private var INSTANCE: FavoriteUserRepository? = null

        fun getInstance(
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors,
            settingPreferences: SettingPreferences
        ):FavoriteUserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteUserRepository(favoriteUserDao, appExecutors, settingPreferences)
            }.also { INSTANCE = it }
    }
}