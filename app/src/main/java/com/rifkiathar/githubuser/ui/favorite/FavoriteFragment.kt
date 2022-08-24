package com.rifkiathar.githubuser.ui.favorite

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseFragment
import com.rifkiathar.githubuser.base.GeneralRecyclerView
import com.rifkiathar.githubuser.databinding.FragmentFavoriteBinding
import com.rifkiathar.githubuser.databinding.ItemUserBinding
import com.rifkiathar.githubuser.model.entity.UserEntity
import com.rifkiathar.githubuser.ui.detail.DetailFragment
import com.rifkiathar.githubuser.utils.GridItemDecorations
import com.rifkiathar.githubuser.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel : FavoriteViewModel by viewModel()

    private val favoriteAdapter by lazy {
        GeneralRecyclerView<UserEntity, ItemUserBinding>(
            diffCallback = diffCallback,
            itemBindingInflater = ItemUserBinding::inflate,
            onBind = { user, _, binding ->
                binding.tvUserName.text = user.name
                binding.ivAvatar.loadImage(user.url)
                binding.ivArrowRight.isVisible = false
            },
            itemListener = { user, _, _ ->
                findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment,
                bundleOf(DetailFragment.USER to user.login))
            }
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        hideToolbar(true)
        initRecyclerView()
        viewModel.getFavoriteData()
    }

    override fun observeData() {
        with(viewModel) {
            observeUserFavorite().onResult {
                favoriteAdapter.setData(it)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
            addItemDecoration(GridItemDecorations(SPACE_DIVIDER, 1))
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val SPACE_DIVIDER = 10
    }
}