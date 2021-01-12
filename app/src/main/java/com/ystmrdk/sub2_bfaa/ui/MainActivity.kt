package com.ystmrdk.sub2_bfaa.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.adapter.UserAdapter
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapterUserData: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var usersData = ArrayList<User>()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = MainViewModel()

        setupRecyclerView()
        search()
        initObserver()
        mainViewModel.fetchUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intentLocale = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLocale)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObserver() {
        mainViewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.INVISIBLE
        })

        mainViewModel.usersData.observe(this, Observer<ArrayList<User>> {
            Log.d("TAG DEBUG", "init: $it")
            usersData.clear()
            usersData.addAll(it)
            rvShow()
        })

        mainViewModel.err.observe(this, Observer<String> {
            Log.d("TAG DEBUG", "err: $it")
        })
    }

    private fun search() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                if (!p0.isEmpty()) {
                    usersData.clear()
                    mainViewModel.fetchSearch(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
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
        userData.let {
            User(
                it.username,
                it.name,
                it.avatar,
                it.company,
                it.location,
                it.repository,
                it.follower,
                it.following,
            )
        }
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, userData)
        startActivity(intent)
    }


}