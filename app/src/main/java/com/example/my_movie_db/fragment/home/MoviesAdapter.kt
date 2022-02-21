package com.example.my_movie_db.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_movie_db.databinding.ItemRvMoviesBinding
import com.example.my_movie_db.models.MovieModel

class MoviesAdapter(private val viewModel: HomeViewModel, private val items: List<MovieModel>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.createViewHolder(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, items[position])

    override fun getItemCount(): Int = if (items.size >= 10) 10 else items.size


    class ViewHolder private constructor(private val binding: ItemRvMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HomeViewModel, item: MovieModel) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun createViewHolder(parent: ViewGroup) = ViewHolder(
                ItemRvMoviesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}