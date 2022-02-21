package com.example.my_movie_db.fragment.popular

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_movie_db.fragment.popular.datasource.PopularDataSource
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Event
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PopularViewModel @Inject constructor(private val dataSource: PopularDataSource) :
    ViewModel() {
    private val _moviesEvent = MutableLiveData<Event<List<MovieModel>>>()
    val moviesEvent: LiveData<Event<List<MovieModel>>>
        get() = _moviesEvent

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>>
        get() = _movies

    private val _navToDetailEvent = MutableLiveData<Event<MovieModel>>()
    val navToDetailEvent: LiveData<Event<MovieModel>>
        get() = _navToDetailEvent

    @SuppressLint("NullSafeMutableLiveData")
    fun getMovies() {
        viewModelScope.launch {
            dataSource.getMovies().let { result ->
                when (result) {
                    is Result.Success -> {
                        _moviesEvent.value = Event(result.data)
                        _movies.value = result.data
                    }
                    is Result.Error -> {
                        Timber.i(result.exception)
                    }
                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    fun search(text: String) {
        val items = arrayListOf<MovieModel>()
        for (movie in _movies.value!!) {
            if (movie.original_title.contains(text, true) or movie.title.contains(text, true)) {
                items.add(movie)
            }
        }
        _moviesEvent.value = Event(items)
    }

    fun onImageClicked(item: MovieModel) {
        _navToDetailEvent.value = Event(item)
    }
}