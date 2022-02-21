package com.example.my_movie_db.fragment.location.datasource

import kotlinx.coroutines.CoroutineDispatcher

class LocationDatasource constructor(
    private val api: LocationApi,
    private val ioDispatcher: CoroutineDispatcher
) : LocationRepository