package com.dicoding.mygithubuser.ui.follow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubuser.data.response.ItemsItem
import com.dicoding.mygithubuser.databinding.ItemUserBinding
import com.dicoding.mygithubuser.ui.detail.DetailActivity

class ListFollowAdapter(private val listUser: ArrayList<ItemsItem>) :
    RecyclerView.Adapter<ListFollowAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item: ItemsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .circleCrop()
                    .into(imgItemUser)
                tvUsername.text = item.login
                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, item.login)
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