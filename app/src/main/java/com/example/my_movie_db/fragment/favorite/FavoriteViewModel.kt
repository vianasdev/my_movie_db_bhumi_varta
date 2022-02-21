package com.example.my_movie_db.fragment.favorite

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.fragment.favorite.datasource.FavoriteDataSource
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.utils.Event
import com.example.my_movie_db.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(val dataSource: FavoriteDataSource) : ViewModel() {
    private val _favoritesEvent = MutableLiveData<Event<List<Favorite>>>()
    val favoritesEvent: LiveData<Event<List<Favorite>>>
        get() = _favoritesEvent

    private val _favorites = MutableLiveData<List<Favorite>>()
    val favorites: LiveData<List<Favorite>>
        get() = _favorites

    private val _navToDetailEvent = MutableLiveData<Event<Favorite>>()
    val navToDetailEvent: LiveData<Event<Favorite>>
        get() = _navToDetailEvent

    @SuppressLint("NullSafeMutableLiveData")
    fun getFavoriteMovies() {
        viewModelScope.launch {
            dataSource.getFavoriteMovies().let { result ->
                when (result) {
                    is Result.Success -> {
                        _favoritesEvent.value = Event(result.data)
                        _favorites.value = result.data
                    }

                    is Result.Error -> Timber.i(result.exception)

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }

    fun getYearFromReleaseDate(releaseDate: String): String = releaseDate.split("-")[0]

    fun search(text: String) {
        val items = arrayListOf<Favorite>()
        for (favorite in _favorites.value!!) {
            if (favorite.title.contains(text, true)) {
                items.add(favorite)
            }
        }
        _favoritesEvent.value = Event(items)
    }

    fun onImageClicked(item: Favorite) {
        _navToDetailEvent.value = Event(item)
    }

    fun removeFromFavorite(item:Favorite){
        viewModelScope.launch {
            dataSource.removeFromFavorite(item).let { result ->
                when(result){
                    is Result.Success -> getFavoriteMovies()

                    is Result.Error -> Timber.i(result.exception)

                    else -> {
                        //Do Nothing
                    }
                }
            }
        }
    }
}