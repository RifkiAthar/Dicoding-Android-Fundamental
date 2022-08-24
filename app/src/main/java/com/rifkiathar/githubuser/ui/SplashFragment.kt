package com.rifkiathar.githubuser.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseFragment
import com.rifkiathar.githubuser.databinding.FragmentSplashBinding

class SplashFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewReady(savedInstanceState: Bundle?) {
        hideToolbar(true)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 2500)
    }

    override fun observeData() {

    }
}