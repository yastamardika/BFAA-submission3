package com.ystmrdk.sub2_bfaa.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import com.ystmrdk.sub2_bfaa.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment(private val callback : SettingCallback) : Fragment() {


    private lateinit var switch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch = switch2
        val state = requireActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE).getBoolean("notif",true)
        switch.setChecked(state)

        switch.setOnCheckedChangeListener { _, stat ->
            callback.onSwitchChange(stat)
        }
    }

    interface SettingCallback {
        fun onSwitchChange(state: Boolean)
    }
}