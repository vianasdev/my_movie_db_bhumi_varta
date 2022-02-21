package com.example.my_movie_db.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_movie_db.databinding.ItemRvBannerBinding
import com.example.my_movie_db.models.MovieModel

class BannerAdapter(private val viewModel: HomeViewModel, private val items: List<MovieModel>) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.createViewHolder(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, items[position])

    override fun getItemCount(): Int =
        if (items.size < 3) items.size else 3

    class ViewHolder private constructor(private val binding: ItemRvBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: HomeViewModel, item: MovieModel) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun createViewHolder(parent: ViewGroup): ViewHolder {
                val layoutInflatter = LayoutInflater.from(parent.context)
                val binding = ItemRvBannerBinding.inflate(layoutInflatter, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}