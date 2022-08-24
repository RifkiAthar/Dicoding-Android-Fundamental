package com.rifkiathar.githubuser.ui.detail

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseFragment
import com.rifkiathar.githubuser.databinding.FragmentDetailBinding
import com.rifkiathar.githubuser.model.response.DetailResponse
import com.rifkiathar.githubuser.ui.detail.adapter.DetailViewPagerAdapter
import com.rifkiathar.githubuser.utils.getDrawableCompat
import com.rifkiathar.githubuser.utils.loadImage
import com.rifkiathar.githubuser.utils.setSafeOnClickListener
import com.rifkiathar.githubuser.utils.shortToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by sharedViewModel()
    private var isFavorite = false
    private var detailUser = DetailResponse()

    private val user: String by lazy {
        arguments?.getString(USER, "") ?: ""
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initViewPager()
        viewModel.getDetail(user)
        initFabListener()
    }

    override fun observeData() {
        with(viewModel) {
            observeDetailResponse().onResult {
                detailUser = it
                initView(detailUser)
            }
            observeLoading().onResult {
                binding.progressbar.isVisible = it
            }
            observeIsFavorite().onResult {
                Timber.d("FAVO: $isFavorite")
                isFavorite = it
                initFabView()
            }
            observeFavoriteMessage().onResult {
                it.getContentIfNotHandled()?.let { isDelete ->
                    if (isDelete) {
                        context?.shortToast(getString(R.string.text_delete_success))
                    } else {
                        context?.shortToast(getString(R.string.text_favorite_success))
                    }
                }
            }
            observeError().onResult {
                activity?.shortToast(it.toString())
            }
        }
    }

    private fun initView(data: DetailResponse) {
        with(binding) {
            tvName.text = data.name
            tvUsername.text = data.login
            tvCompany.text = data.company
            ivAvatar.loadImage(data.avatar)
        }
    }

    private fun initViewPager() {
        val detailViewsPagerAdapter = DetailViewPagerAdapter(activity as AppCompatActivity, user)
        binding.viewPager.adapter = detailViewsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


    }

    private fun initFabView() {
        val drawable =
            if (isFavorite) context?.getDrawableCompat(R.drawable.ic_favorite_favorite_filled)
            else context?.getDrawableCompat(R.drawable.ic_favorite_favorite_unfilled)
        binding.fabFav.apply {
            setImageDrawable(drawable)
        }
    }

    private fun initFabListener() {
        binding.fabFav.setSafeOnClickListener {
            if (isFavorite) {
                viewModel.favorite(detailUser, true)
            } else {
                viewModel.favorite(detailUser, false)
            }
        }
    }

    companion object {
        const val USER = "user_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
}