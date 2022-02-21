package com.example.my_movie_db.fragment.location

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_movie_db.fragment.location.datasource.LocationDatasource
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val dataSource: LocationDatasource) :
    ViewModel() {
    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location

    fun setLocation(location: Location){
        _location.value = location
    }
}