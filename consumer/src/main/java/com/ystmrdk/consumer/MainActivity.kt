package com.ystmrdk.consumer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.ystmrdk.consumer.DatabaseContract.CONTENT_URI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        loadUsersAsync()
    }

    private fun setupRecyclerView() {
        recycleView.layoutManager = LinearLayoutManager(recycleView.context)
        recycleView.setHasFixedSize(true)
        recycleView.addItemDecoration(
            DividerItemDecoration(recycleView.context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                Log.d("CEK", "cursor $cursor")
                cursor?.let {
                    UserMappingHelper.mapCursorToArrayList(cursor)
                }
            }
            val users = deferredUsers.await()
            users?.let {
                if (users.isNotEmpty()) {
                    val adapter = UserAdapter(it)
                    recycleView.adapter = adapter
                }
            }
        }
    }
}
