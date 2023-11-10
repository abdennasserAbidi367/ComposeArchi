package com.example.composearchitecture.feature.home

import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.composearchitecture.data.Puppy

class HomeAdapter : ListAdapter<Puppy, HomeAdapter.HomeViewHolder>(differCallback) {

    inner class HomeViewHolder(val composeView: ComposeView): ViewHolder(composeView) {

        fun bind(input: String) {
            composeView.setContent {
                MaterialTheme {
                    Text(input)
                }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Puppy>() {
            override fun areItemsTheSame(oldItem: Puppy, newItem: Puppy): Boolean {
                return oldItem == oldItem
            }

            override fun areContentsTheSame(oldItem: Puppy, newItem: Puppy): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind("$position")
    }
}