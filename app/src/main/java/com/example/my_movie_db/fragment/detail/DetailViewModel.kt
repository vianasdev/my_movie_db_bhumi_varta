package com.example.my_movie_db.fragment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_movie_db.R
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.fragment.detail.datasource.DetailDataSource
import com.example.my_movie_db.fragment.detail.model.Cast
import com.example.my_movie_db.fragment.detail.model.DetailModel
import com.example.my_movie_db.utils.Event
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataSource: DetailDataSource) : ViewModel() {
    private val _detail = MutableLiveData<DetailModel>()
    val detail: LiveData<DetailModel>
        get() = _detail

    private val _isFavorited = MutableLiveData(false)
    val isFavorited: LiveData<Boolean>
        get() = _isFavorited

    private val _detailEvent = MutableLiveData<Event<DetailModel>>()
    val detailEvent: LiveData<Event<DetailModel>>
        get() = _detailEvent

    private val _castsEvent = MutableLiveData<Event<List<Cast>>>()
    val castsEvent: LiveData<Event<List<Cast>>>
        get() = _castsEvent

    private val _backClickedEvent = MutableLiveData<Event<Boolean>>()
    val backClickedEvent: LiveData<Event<Boolean>>
        get() = _backClickedEvent

    private val _favoriteButtonEvent = MutableLiveData<Event<Boolean>>()
    val favoriteButtonEvent: LiveData<Event<Boolean>>
        get() = _favoriteButtonEvent

    @SuppressLint("NullSafeMutableLiveData")
    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            dataSource.getMovieDetail(movieId).let { result ->
                when (result) {
                    is Result.Success -> {
                        _detail.value = result.data
                        _detailEvent.value = Event(result.data)
                    }
                    is Result.Error -> {
                        Timber.i(result.exception)
                    }
                    else -> {
                        //Do Nothing
                    }
                }
            }
            dataSource.getMovieCredit(movieId).let { result ->
                when (result) {
                    is Result.Success -> {
                        _castsEvent.value = Event(result.data)
                    }
                    is Result.Error -> {
                        Timber.i(result.exception)
                    }
                    else -> {
                        //Do Nothing
                    }
                }
            }

            dataSource.getFavoriteMovies().let { result ->
                when (result) {
                    is Result.Success -> {
                        val isFavorited = isMovieExistInFavorite(movieId, result.data)
                        _favoriteButtonEvent.value = Event(isFavorited)
                        _isFavorited.value = isFavorited

                    }

                    is Result.Error -> Timber.i(result.exception)

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    fun toggleFavoriteButton(item: DetailModel) {
        if (_isFavorited.value!!) {
            removeFromFavorite(item)
        } else {
            insertToFavorite(item)
        }
    }

    fun insertToFavorite(item: DetailModel) {
        var genres = ""
        for (i in item.genres.indices) {
            genres += item.genres[i].name
            if (i < item.genres.size - 1) {
                genres += ", "
            }
        }
        val favorite = Favorite(
            item.id, item.title, item.release_date, genres, item.backdrop_path
        )

        viewModelScope.launch {
            dataSource.insertToFavorite(favorite).let { result ->
                when (result) {
                    is Result.Success -> {
                        _favoriteButtonEvent.value = Event(true)
                        _isFavorited.value = true
                    }

                    is Result.Error -> {
                        Timber.i(result.exception)
                        _favoriteButtonEvent.value = Event(false)
                        _isFavorited.value = false
                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun removeFromFavorite(item: DetailModel) {
        viewModelScope.launch {
            dataSource.removeFromFavorite(item).let { result ->
                when(result){
                    is Result.Success -> {
                        _favoriteButtonEvent.value = Event(false)
                        _isFavorited.value = false
                    }

                    is Result.Error -> {
                        Timber.i(result.exception)
                        _favoriteButtonEvent.value = Event(true)
                        _isFavorited.value = true
                    }

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    private fun isMovieExistInFavorite(movieId: String, favorites: List<Favorite>) : Boolean {
        var exist = false
        for (favorite in favorites) {
            if (movieId == favorite.id) {
                exist = true
            }
        }
        return exist
    }

    fun showProfileNameItem(profileName: String) = profileName.replace(" ", "\n")

    fun showRuntime(runtime: Int): String {
        var hour = 0
        var minute = runtime
        while (minute >= 60) {
            minute -= 60
            hour += 1
        }
        return if (hour == 0) "${minute}m" else if (minute == 0) "${hour}h" else "${hour}h ${minute}m"
    }

    fun generateGenresView(context: Context, layout: LinearLayout) {
        for (i in _detail.value!!.genres.indices) {
            val tvGenre = TextView(context)
            tvGenre.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER_VERTICAL }
            tvGenre.setTextColor(Color.parseColor("#B3FFFFFF"))
            tvGenre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            tvGenre.text = detail.value!!.genres[i].name

            val ivSplitter = ImageView(context)
            val dp5 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5f,
                context.resources.displayMetrics
            ).toInt()

            ivSplitter.layoutParams = LinearLayout.LayoutParams(dp5, dp5).apply {
                gravity = Gravity.CENTER_VERTICAL
                marginStart = dp5
                marginEnd = dp5
            }
            ivSplitter.setBackgroundResource(R.drawable.ic_genresplitter)

            layout.addView(tvGenre)

            if (i < _detail.value!!.genres.size - 1) {
                layout.addView(ivSplitter)
            }
        }
    }

    fun onBackClicked() {
        _backClickedEvent.value = Event(true)
    }
}