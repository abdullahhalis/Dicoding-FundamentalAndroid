package com.dicoding.mygithubuser.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.databinding.ItemUserBinding
import com.dicoding.mygithubuser.ui.detail.DetailActivity
import com.dicoding.mygithubuser.utils.loadImage

class ListAdapter(private val listUser: List<Any>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            if (item is ItemsItem) {
                setList(item.login, item.avatarUrl)
            }else if (item is FavoriteUser) {
                setList(item.username, item.avatarUrl)
            }
        }

        private fun setList(username: String, avatarUrl: String){
            binding.apply {
                imgItemUser.loadImage(avatarUrl)
                tvUsername.text = username
                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, username)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}