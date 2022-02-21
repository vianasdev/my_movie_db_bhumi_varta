package com.example.my_movie_db.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.my_movie_db.databinding.FragmentHomeBinding
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.ui.main.MainActivity
import com.example.my_movie_db.utils.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentHomeBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = viewModel

        observeViewModel()
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovies()
    }

    private fun observeViewModel() {
        viewModel.bannersEvent.observe(viewLifecycleOwner, EventObserver {
            setupBannerAdapter(it)
        })

        viewModel.popularsEvent.observe(viewLifecycleOwner, EventObserver {
            setupPopularAdapter(it)
        })

        viewModel.commingSoonsEvent.observe(viewLifecycleOwner, EventObserver {
            setupCommingSoonAdapter(it)
        })

        viewModel.navToDetailEvent.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).navToDetail(it.id)
        })
    }

    private fun setupBannerAdapter(banners: List<MovieModel>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = BannerAdapter(viewModel, banners)
            viewDataBinding.rvBanner.adapter = adapter
        }
    }

    private fun setupPopularAdapter(populars: List<MovieModel>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = MoviesAdapter(viewModel, populars)
            viewDataBinding.rvCaster.adapter = adapter
        }
    }

    private fun setupCommingSoonAdapter(commingSoons: List<MovieModel>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = MoviesAdapter(viewModel, commingSoons)
            viewDataBinding.rvCommingSoon.adapter = adapter
        }
    }

}