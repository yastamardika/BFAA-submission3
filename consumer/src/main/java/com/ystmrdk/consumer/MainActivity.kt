package com.ystmrdk.consumer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycleView.layoutManager = LinearLayoutManager(recycleView.context)
        recycleView.setHasFixedSize(true)
        recycleView.addItemDecoration(
            DividerItemDecoration(recycleView.context, DividerItemDecoration.VERTICAL)
        )
    }
}
