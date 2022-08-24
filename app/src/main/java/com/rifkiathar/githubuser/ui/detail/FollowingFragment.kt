package com.rifkiathar.githubuser.ui.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseFragment
import com.rifkiathar.githubuser.base.GeneralRecyclerView
import com.rifkiathar.githubuser.databinding.FragmentFollowingBinding
import com.rifkiathar.githubuser.databinding.ItemUserBinding
import com.rifkiathar.githubuser.model.response.UserResponse
import com.rifkiathar.githubuser.utils.GridItemDecorations
import com.rifkiathar.githubuser.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class FollowingFragment : BaseFragment<FragmentFollowingBinding>(FragmentFollowingBinding::inflate) {

    private val viewModel : DetailViewModel by sharedViewModel()

    private val user: String by lazy {
        arguments?.getString(USER_TAG, "") ?: ""
    }

    private val followingAdapter by lazy {
        GeneralRecyclerView<UserResponse, ItemUserBinding>(
            diffCallback = diffCallback,
            itemBindingInflater = ItemUserBinding::inflate,
            onBind = { user, _, binding ->
                binding.tvUserName.text = user.login
                binding.ivAvatar.loadImage(user.avatarUrl)
                binding.ivArrowRight.isVisible = false
            },
            itemListener = { user, _, _ ->
                findNavController().navigate(
                    R.id.action_detailFragment_to_detailFragment,
                    bundleOf(DetailFragment.USER to user.login)
                )
            }
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel.getUserFollowing(user)
    }

    override fun observeData() {
        with(viewModel) {
            observeFollowing().onResult {
                initView(it)
            }
        }
    }

    private fun initView(data: List<UserResponse>) {
        with(binding.rvSearchFollowing) {
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter.apply {
                setData(data)
            }
            addItemDecoration(GridItemDecorations(SPACE_DIVIDER, 1))
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val SPACE_DIVIDER = 10
        private const val USER_TAG = "FollowingFragment.user"
        fun newInstance(user: String): FollowingFragment {
            val bundle = Bundle().apply {
                putString(USER_TAG, user)
            }
            return FollowingFragment().apply {
                arguments = bundle
            }
        }
    }
}