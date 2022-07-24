package com.silverbullet.asteroidsradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silverbullet.asteroidsradar.R
import com.silverbullet.asteroidsradar.databinding.AsteroidListItemBinding
import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.utils.TimeUtils
import timber.log.Timber
import java.text.SimpleDateFormat

class HomeListAdapter :
    ListAdapter<Asteroid, HomeListAdapter.AsteroidItemViewHolder>(AsteroidItemCallback()) {

    private var asteroidsList = emptyList<Asteroid>()
    private val asteroidItemClickListener = AsteroidItemOnClickListener()

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

        fun bind(asteroid: Asteroid,asteroidItemClickListener: AsteroidItemOnClickListener) {
            binding.asteroid = asteroid
            asteroidItemClickListener.listener?.let { callback->
                binding.root.setOnClickListener {
                    callback(asteroid)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return AsteroidItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid,asteroidItemClickListener)
    }

    fun setAsteroidItemClickListener(listener: (Asteroid)->Unit){
        asteroidItemClickListener.setClickListener(listener)
    }

    fun addNewList(asteroids: List<Asteroid>){
        asteroidsList = asteroids
        submitList(asteroidsList)
    }

    fun applyFilter(id: Int){
            when(id){
                R.id.all->{
                    submitList(asteroidsList)
                }
                R.id.today->{
                    val newList = asteroidsList.filter { it.closeApproachDate == TimeUtils.today }.toList()
                    submitList(newList)
                }
                R.id.this_week->{
                    val newList = asteroidsList.filter {
                        val formatter = SimpleDateFormat("yyyy-MM-dd")
                        val today = formatter.parse(TimeUtils.today)
                        val asteroidDate =formatter.parse( it.closeApproachDate)
                        asteroidDate.after(today)
                    }.toList()
                    submitList(newList)
                }

        }
    }

    inner class AsteroidItemOnClickListener {
        var listener: ((Asteroid) -> Unit)? = null

        fun setClickListener(listener: (Asteroid) -> Unit) {
            this@AsteroidItemOnClickListener.listener = listener
        }
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