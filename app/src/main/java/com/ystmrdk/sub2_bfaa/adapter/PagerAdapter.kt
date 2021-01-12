package com.ystmrdk.sub2_bfaa.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.ui.fragment.FollowerFragment
import com.ystmrdk.sub2_bfaa.ui.fragment.FollowingFragment

class PagerAdapter ( private val context: Context , fragmentManager: FragmentManager ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment {
        var frag: Fragment? = null
        when(position){
            0 -> frag = FollowerFragment()
            1 -> frag = FollowingFragment()
        }
        return frag as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

}