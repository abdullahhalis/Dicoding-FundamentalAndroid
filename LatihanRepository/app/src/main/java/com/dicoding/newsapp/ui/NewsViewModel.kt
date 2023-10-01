package com.dicoding.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getHeadLineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news, true)
        }
    }

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news, false)
        }
    }
}