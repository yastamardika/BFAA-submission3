package com.ystmrdk.sub2_bfaa.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.ystmrdk.sub2_bfaa.adapter.PagerAdapter
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.receiver.AlarmReceiver
import com.ystmrdk.sub2_bfaa.receiver.AlarmReceiver.Companion.NOTIF_ID
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        setupViewPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intentLocale = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLocale)
            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager() {
        val sectionPager = PagerAdapter(this, supportFragmentManager)
        vp2.adapter = sectionPager
        tl_foll.setupWithViewPager(vp2)
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun init() {
        val userData = intent.getParcelableExtra<User>(EXTRA_DATA)
        userData?.let {
            title = it.name
            tv_username.text = it.username
            tv_repository.text = "Repository : ${it.repository}"
            tv_company.text = "Company : ${it.company}"
            tv_location.text = "Location : ${it.location}"
        }
        Glide.with(this).load(userData?.avatar).into(iv_avatar)
    }


}