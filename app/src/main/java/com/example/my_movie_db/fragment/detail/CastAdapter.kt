package com.example.my_movie_db.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_movie_db.databinding.ItemRvCasterBinding
import com.example.my_movie_db.fragment.detail.model.Cast

class CastAdapter(
    private val viewModel: DetailViewModel,
    private val items: List<Cast>
) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder.createViewHolder(
            parent
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) =
        holder.bind(viewModel, items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder private constructor(private val binding: ItemRvCasterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            viewModel: DetailViewModel,
            item: Cast
        ) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun createViewHolder(parent: ViewGroup): ViewHolder =
                ViewHolder(
                    ItemRvCasterBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }
    }
}