package com.silverbullet.asteroidsradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silverbullet.asteroidsradar.databinding.AsteroidListItemBinding
import com.silverbullet.asteroidsradar.model.Asteroid

class HomeListAdapter :
    ListAdapter<Asteroid, HomeListAdapter.AsteroidItemViewHolder>(AsteroidItemCallback()) {

    class AsteroidItemViewHolder private constructor(private val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): AsteroidItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val asteroidItemBinding = AsteroidListItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return AsteroidItemViewHolder(asteroidItemBinding)
            }
        }

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return AsteroidItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
    }
}

class AsteroidItemCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}