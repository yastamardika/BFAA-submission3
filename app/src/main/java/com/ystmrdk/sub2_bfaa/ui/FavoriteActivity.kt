package com.ystmrdk.sub2_bfaa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.adapter.UserAdapter
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.viewmodel.FavoriteViewModel
import com.ystmrdk.sub2_bfaa.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapterUserData: UserAdapter
    private lateinit var viewModel: FavoriteViewModel
    private var usersData = ArrayList<User>()

    companion object {
        private val TAG = FavoriteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setupActionBar()
        setupViewModel()
        setupRecyclerView()
        initObserver()
    }

    private fun setupActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite"
    }

    private fun setupViewModel(){
        viewModel = FavoriteViewModel()
        viewModel.init(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intentLocale = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLocale)
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObserver() {
        viewModel.favorites.observe(this, Observer<List<User>> {
            usersData.clear()
            usersData.addAll(it)
            rvShow()
        })
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

        adapterUserData.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(userData: User) {
                selectUser(userData)
            }
        })
    }

    private fun selectUser(userData: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, userData)
        startActivity(intent)
    }

}