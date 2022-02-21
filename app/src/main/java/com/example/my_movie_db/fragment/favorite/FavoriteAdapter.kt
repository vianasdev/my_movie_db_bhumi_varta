package com.example.my_movie_db.fragment.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.databinding.ItemRvFavoriteBinding

class FavoriteAdapter(
    private val viewModel: FavoriteViewModel,
    private val items: List<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.createViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder private constructor(private val binding: ItemRvFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: FavoriteViewModel, item: Favorite) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun createViewHolder(parent: ViewGroup) = ViewHolder(
                ItemRvFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}