package com.rifkiathar.githubuser.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rifkiathar.githubuser.utils.DiffCallback
import com.rifkiathar.githubuser.utils.setSafeOnClickListener

class GeneralRecyclerView<T: Any, VB: ViewBinding>(
    private val diffCallback: DiffCallback,
    private val itemBindingInflater: (
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) -> VB,
    private val fadeAnimation: Boolean = true,
    private val onBind: (T, pos: Int, binding: VB) -> Unit,
    private val itemListener: (T, pos: Int, binding: VB) -> Unit = { _, _, _ -> kotlin.run {} }
) : RecyclerView.Adapter<GeneralRecyclerView<T, VB>.GeneralViewHolder<T, VB>>() {

    private val listData = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralViewHolder<T, VB> {
        val from = LayoutInflater.from(parent.context)
        val binding = itemBindingInflater.invoke(from, parent, false)
        return GeneralViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneralViewHolder<T, VB>, position: Int) {
        if (fadeAnimation) {
            setFadeAnimation(holder.itemView)
        }

        holder.bindView(
            listData[holder.adapterPosition],
            onBind,
            itemListener
        )
    }

    override fun getItemCount(): Int = listData.size

    fun updateData(datas: List<T>) {
        with(listData) {
            clearData()
            addAll(datas)
        }
        notifyItemRangeInserted(0, datas.size)
    }

    fun setData(datas: List<T>) {
        calculateDiff(datas)
    }

    fun addData(newDatas: List<T>) {
        val list = ArrayList(this.listData)
        list.addAll(newDatas)
        calculateDiff(list)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSingleData(newDatas: T) {
        listData.add(newDatas)
        notifyDataSetChanged()
    }

    fun clearData() {
        calculateDiff(emptyList())
    }

    private fun calculateDiff(newDatas: List<T>) {
        diffCallback.setList(listData, newDatas)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newDatas)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class GeneralViewHolder<T: Any, VB: ViewBinding>(
        private val itemBinding: VB
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun bindView(
            item: T,
            onBind: (T, pos: Int, binding: VB) -> Unit,
            itemListener: (T, pos: Int, binding: VB) -> Unit
        ) {
            with(itemView) {
                onBind.invoke(item, adapterPosition, itemBinding)
                setSafeOnClickListener {
                    itemListener.invoke(item, adapterPosition, itemBinding)
                }
            }
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 400
        view.startAnimation(anim)
    }
}