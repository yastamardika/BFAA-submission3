package com.ystmrdk.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ystmrdk.consumerapp.adapter.UserAdapter
import com.ystmrdk.consumerapp.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var adapterUserData: UserAdapter
    private var usersData = ArrayList<User>()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        initObserver()
    }


    private fun setupRecyclerView() {
        recycleView.layoutManager = LinearLayoutManager(recycleView.context)
        recycleView.setHasFixedSize(true)
        recycleView.addItemDecoration(
            DividerItemDecoration(recycleView.context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun rvShow() {
        recycleView.layoutManager = LinearLayoutManager(this)
        adapterUserData = UserAdapter(usersData)
        recycleView.adapter = adapterUserData
    }
}