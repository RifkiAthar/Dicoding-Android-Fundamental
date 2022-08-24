package com.rifkiathar.githubuser.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecorations(private val gridSpacing: Int, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    private var needLeftSpacing = false

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val frameWidth = (parent.width - gridSpacing * (spanCount - 1)) / spanCount
        val padding = parent.width / spanCount - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.top = if (itemPosition < spanCount) 0 else gridSpacing

        when {
            itemPosition % gridSpacing == 0 -> {
                outRect.apply {
                    left = 0
                    right = padding
                }
                needLeftSpacing = true
            }
            (itemPosition + 1) % spanCount == 0 -> {
                needLeftSpacing = false
                outRect.apply {
                    right = 0
                    left = padding
                }
            }
            needLeftSpacing -> {
                needLeftSpacing = false
                outRect.apply {
                    left = gridSpacing - padding
                    right =
                        if ((itemPosition + 2) % spanCount == 0) gridSpacing - padding else gridSpacing / 2
                }
            }
            (itemPosition + 2) % gridSpacing == 0 -> {
                needLeftSpacing = false
                outRect.apply {
                    left = gridSpacing / 2
                    right = gridSpacing - padding
                }
            }
            else -> {
                needLeftSpacing = false
                outRect.apply {
                    left = gridSpacing / 2
                    right = gridSpacing / 2
                }
            }
        }
        outRect.bottom = 0
    }
}