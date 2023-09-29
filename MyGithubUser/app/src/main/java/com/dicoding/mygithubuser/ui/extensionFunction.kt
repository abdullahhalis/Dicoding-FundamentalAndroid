package com.dicoding.mygithubuser.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ProgressBar.showLoading(isLoading: Boolean){
    this.visibility = if (isLoading) View.VISIBLE else View.GONE
}