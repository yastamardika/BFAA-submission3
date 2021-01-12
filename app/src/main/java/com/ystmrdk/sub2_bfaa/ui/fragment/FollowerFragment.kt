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
import com.ystmrdk.sub2_bfaa.adapter.FollowerAdapter
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.viewmodel.FollowerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {
    companion object {
        const val EXTRA_DATA = "data"
    }

    private val usersData: ArrayList<User> = ArrayList()
    private lateinit var adapter: FollowerAdapter
    private lateinit var viewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FollowerViewModel()
        usersData.clear()
        initObserver()
        val userData = activity!!.intent.getParcelableExtra(EXTRA_DATA) as User?
        viewModel.fetchFollower(userData?.username.toString())
    }

    private fun initObserver() {
        viewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) progressBarFollower.visibility = View.VISIBLE
            else progressBarFollower.visibility = View.INVISIBLE
        })

        viewModel.usersData.observe(this, Observer<ArrayList<User>> {
            Log.d("TAG DEBUG Follower", "init: $it")
            usersData.clear()
            usersData.addAll(it)
            rvShow()
        })

        viewModel.err.observe(this, Observer<String> {
            Log.d("TAG DEBUG Follower", "err: $it")
        })
    }

    private fun rvShow() {
        recycleViewFollower.layoutManager = LinearLayoutManager(activity)
        adapter = FollowerAdapter(usersData)
        recycleViewFollower.adapter = adapter
    }
}