package com.ystmrdk.sub2_bfaa.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.adapter.FollowingAdapter
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_DATA = "data"
    }

    private val usersData: ArrayList<User> = ArrayList()
    private lateinit var adapter: FollowingAdapter
    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FollowingViewModel()
        usersData.clear()
        initObserver()
        val userData = activity!!.intent.getParcelableExtra(EXTRA_DATA) as User?
        viewModel.fetchFollowing(userData?.username.toString())
    }

    private fun initObserver() {
        viewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) progressBarFollowing.visibility = View.VISIBLE
            else progressBarFollowing.visibility = View.INVISIBLE
        })

        viewModel.usersData.observe(this, Observer<ArrayList<User>> {
            Log.d("TAG DEBUG", "init: $it")
            usersData.clear()
            usersData.addAll(it)
            rvShow()
        })

        viewModel.err.observe(this, Observer<String> {
            Log.d("TAG DEBUG", "err: $it")
        })
    }

    private fun rvShow() {
        recycleViewFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = FollowingAdapter(usersData)
        recycleViewFollowing.adapter = adapter
    }
}