package com.example.my_movie_db.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:glideImage")
fun setGlideImage(imageView: ImageView, imagePath: String?) {
    imagePath?.let {
        Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w500$imagePath")
            .into(imageView)
    }
}

@BindingAdapter("app:glideImageCircle")
fun setGlideImageCircle(imageView: ImageView, imagePath: String?) {
    imagePath?.let {
        Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w500$imagePath").apply(
            RequestOptions.circleCropTransform())
            .into(imageView)
    }
}

