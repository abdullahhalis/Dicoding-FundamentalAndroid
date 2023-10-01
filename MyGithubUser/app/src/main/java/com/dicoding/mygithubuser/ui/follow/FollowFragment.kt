package com.dicoding.mygithubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.databinding.FragmentFollowBinding
import com.dicoding.mygithubuser.ui.detail.DetailViewModel
import com.dicoding.mygithubuser.ui.list.ListAdapter
import com.dicoding.mygithubuser.utils.showLoading


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 1)
        val username = arguments?.getString(ARG_USERNAME)

        detailViewModel.getListFollowers(username)
        detailViewModel.getListFollwing(username)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.showLoading(it)
        }

        if (position == 1){
            detailViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                setListUser(listFollowers)
            }

        } else {
            detailViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setListUser(listFollowing)
            }
        }


    }

    private fun setListUser(itemsItem: ArrayList<ItemsItem>) {
        val listFollowAdapter = ListAdapter(itemsItem)
        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            rvFollow.adapter = listFollowAdapter
        }
    }

    companion object {
        const val TAG = "FollowFragment"
        const val ARG_POSITION = "postion"
        const val ARG_USERNAME = "abdullahhalis"
    }
}