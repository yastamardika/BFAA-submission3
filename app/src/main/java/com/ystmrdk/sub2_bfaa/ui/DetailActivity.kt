package com.ystmrdk.sub2_bfaa.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.adapter.PagerAdapter
import com.ystmrdk.sub2_bfaa.db.FavoriteDatabase
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.viewmodel.DetailViewModel
import com.ystmrdk.sub2_bfaa.receiver.AlarmReceiver
import com.ystmrdk.sub2_bfaa.receiver.AlarmReceiver.Companion.NOTIF_ID
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "data"
    }

    private lateinit var detailViewModel: DetailViewModel

    private var userData: User? = null
    private var menuFavorite: MenuItem? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupActionBar()
        setupViewModel()
        init()
        initObserver()
        setupViewPager()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        detailViewModel = DetailViewModel()
        detailViewModel.init(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuFavorite = menu?.findItem(R.id.action_favorite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val intentLocale = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intentLocale)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_favorite -> {
                toggleFavorite()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuFavorite = menu?.findItem(R.id.action_favorite)
        checkUser()
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setupViewPager() {
        val sectionPager = PagerAdapter(this, supportFragmentManager)
        vp2.adapter = sectionPager
        tl_foll.setupWithViewPager(vp2)
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun init() {
        userData = intent.getParcelableExtra(EXTRA_DATA)
        userData?.let {
            checkUser()
            title = it.name
            tv_username.text = it.username
            tv_repository.text = "Repository : ${it.repository}"
            tv_company.text = "Company : ${it.company}"
            tv_location.text = "Location : ${it.location}"
            detailViewModel.setUserData(it)
        }
        Glide.with(this).load(userData?.avatar).into(iv_avatar)
    }


    private fun initObserver() {
        detailViewModel.count.observe(this, Observer<Int> {
            if (it > 0) {  // the user is already added to favorite
                isFavorite = true
                menuFavorite?.setIcon(R.drawable.ic_baseline_favorite_24)
            } else {
                isFavorite = false
                menuFavorite?.setIcon(R.drawable.ic_baseline_favorite_border_24)
            }
        })
    }

    private fun checkUser() {
        userData?.let {
            detailViewModel.getCount(it.id)
        }
    }

    private fun toggleFavorite(){
        if(isFavorite){
            menuFavorite?.setIcon(R.drawable.ic_baseline_favorite_border_24)
            detailViewModel.deleteFromFavorite()
        } else {
            menuFavorite?.setIcon(R.drawable.ic_baseline_favorite_24)
            detailViewModel.addToFavorite()
        }
        isFavorite = !isFavorite
        checkUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        FavoriteDatabase.destroyDatabase()
    }

}