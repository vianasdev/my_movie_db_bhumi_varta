package com.example.my_movie_db.fragment.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_movie_db.databinding.ItemRvPopularBinding
import com.example.my_movie_db.models.MovieModel

class MoviesAdapter(private val viewModel: PopularViewModel, private val items: List<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.createViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder private constructor(private val binding: ItemRvPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: PopularViewModel, item: MovieModel) {
            binding.item = item
            binding.viewModel = viewModel
            binding.executePendingBindings()

        }

        companion object {
            fun createViewHolder(parent: ViewGroup) = ViewHolder(
                ItemRvPopularBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}