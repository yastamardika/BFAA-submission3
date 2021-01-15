package com.ystmrdk.sub2_bfaa.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SwitchCompat
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.ui.MainActivity.Companion.callback
import com.ystmrdk.sub2_bfaa.ui.fragment.SettingFragment
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var switch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        switch = switch2
        val state = this.getSharedPreferences("Setting", Context.MODE_PRIVATE).getBoolean("notif",true)
        Log.d("CEK", "state: $state")
        switch.isChecked = state

        switch.setOnCheckedChangeListener { _, stat ->
            callback.onSwitchChange(stat)
        }
    }

    interface SettingCallback {
        fun onSwitchChange(state: Boolean)
    }

}