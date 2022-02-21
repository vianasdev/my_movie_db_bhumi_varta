package com.example.my_movie_db.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_movie_db.fragment.home.datasource.HomeDataSource
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Event
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(val dataSource: HomeDataSource) : ViewModel() {
    private val _bannersEvent = MutableLiveData<Event<List<MovieModel>>>()
    val bannersEvent: MutableLiveData<Event<List<MovieModel>>>
        get() = _bannersEvent

    private val _popularsEvent = MutableLiveData<Event<List<MovieModel>>>()
    val popularsEvent: MutableLiveData<Event<List<MovieModel>>>
        get() = _popularsEvent

    private val _commingSoonsEvent = MutableLiveData<Event<List<MovieModel>>>()
    val commingSoonsEvent: MutableLiveData<Event<List<MovieModel>>>
        get() = _commingSoonsEvent

    private val _navToDetailEvent = MutableLiveData<Event<MovieModel>>()
    val navToDetailEvent: LiveData<Event<MovieModel>>
        get() = _navToDetailEvent

    fun getMovies() {
        viewModelScope.launch {
            dataSource.getMovies().let { result ->
                when (result) {
                    is Result.Success -> {
                        _bannersEvent.value = Event(result.data)
                        sortPopularities(result.data)
                    }
                    is Result.Error -> {
                        Timber.i(result.exception)
                    }
                    else -> {
                        //Do Nothing
                    }
                }
            }
            dataSource.getCommingSoonMovies().let { result ->
                when (result) {
                    is Result.Success -> {
                        _commingSoonsEvent.value = Event(result.data)
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

    private fun sortPopularities(items: List<MovieModel>) {
        val sortedItems = items.sortedByDescending { it.popularity }
        _popularsEvent.value = Event(sortedItems)
    }

    fun onImageClicked(item: MovieModel) {
        _navToDetailEvent.value = Event(item)
    }

}