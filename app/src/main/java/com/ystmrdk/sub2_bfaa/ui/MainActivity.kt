package com.ystmrdk.sub2_bfaa.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
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
import com.ystmrdk.sub2_bfaa.receiver.AlarmReceiver
import com.ystmrdk.sub2_bfaa.ui.fragment.SettingFragment
import com.ystmrdk.sub2_bfaa.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SettingActivity.SettingCallback {
    private lateinit var adapterUserData: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var usersData = ArrayList<User>()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        lateinit var callback: SettingActivity.SettingCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = MainViewModel()
        callback = this

        setupRecyclerView()
        search()
        initObserver()
        mainViewModel.fetchUser()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        notificationSetup()
//        dailyAlarmSetup()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intentLocale = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLocale)
        } else if (item.itemId == R.id.settings) {
            val intentSettings = Intent(this, SettingActivity::class.java)
            startActivity(intentSettings)
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
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, userData)
        startActivity(intent)
    }

    private fun notificationSetup() {
        val preference = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val editor = preference.edit()
        if (!preference.contains("notif")) {
            editor.putBoolean("notif", true)
            onSwitchChange(true)
        }
        editor.apply()
    }

    override fun onSwitchChange(b: Boolean) {
        if (b) {
            Log.d("CEK", "if executed $b")
            dailyAlarmSetup()
        } else {
            Log.d("CEK", "else executed $b")
            stopDailyAlarm()
        }
        Log.d("CEK", "executed $b")
        val preference = getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("notif", b)
        editor.apply()

    }

    private fun stopDailyAlarm() {
        Log.d("CEK", "stopDailyAlarm()")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.NOTIF_ID, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

    private fun dailyAlarmSetup() {
        Log.d("CEK", "dailyAlarmSetup()")
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 15)
        calendar.set(Calendar.MINUTE, 50)
        calendar.set(Calendar.SECOND, 0)


        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.NOTIF_ID, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}