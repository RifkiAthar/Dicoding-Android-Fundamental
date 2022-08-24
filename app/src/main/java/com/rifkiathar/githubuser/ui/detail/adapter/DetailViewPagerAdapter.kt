package com.rifkiathar.githubuser.ui.detail.adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rifkiathar.githubuser.ui.detail.FollowersFragment
import com.rifkiathar.githubuser.ui.detail.FollowingFragment

class DetailViewPagerAdapter(
    activity: AppCompatActivity,
    private val login : String
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowingFragment.newInstance(login)
            1 -> fragment = FollowersFragment.newInstance(login)
        }
        return fragment as Fragment
    }

}