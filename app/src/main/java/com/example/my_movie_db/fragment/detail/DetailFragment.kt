package com.example.my_movie_db.fragment.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.my_movie_db.R
import com.example.my_movie_db.databinding.FragmentDetailBinding
import com.example.my_movie_db.fragment.detail.model.Cast
import com.example.my_movie_db.fragment.home.HomeFragment
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.ui.main.MainActivity
import com.example.my_movie_db.utils.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    companion object {
        const val MOVIE_ID = "movieId"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentDetailBinding.inflate(inflater)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

        observeViewModel()
        setupOnBackClicked()

        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieDetail(requireArguments().getString(MOVIE_ID)!!)
    }

    private fun observeViewModel() {
        viewModel.castsEvent.observe(viewLifecycleOwner, EventObserver {
            setupCasterAdapter(it)
        })

        viewModel.detailEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.generateGenresView(requireContext(), viewDataBinding.llGenres)

        })

        viewModel.backClickedEvent.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).replaceFragment(HomeFragment())
        })

        viewModel.favoriteButtonEvent.observe(viewLifecycleOwner, EventObserver {
            setFavoriteButton(it)
        })
    }

    private fun setupCasterAdapter(items: List<Cast>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = CastAdapter(
                viewModel,
                items
            )
            viewDataBinding.rvCaster.adapter = adapter
        }
    }

    private fun setupOnBackClicked() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackClicked()
            }
        })
    }

    private fun setFavoriteButton(isFavorited: Boolean) {
        if (isFavorited) {
            viewDataBinding.ivFav.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_delete
                )
            )
            viewDataBinding.tvFav.text = "Remove from favorite"
            viewDataBinding.tvFav.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        } else {
            viewDataBinding.ivFav.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_add
                )
            )
            viewDataBinding.tvFav.text = "Add to favorite"
            viewDataBinding.tvFav.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        }
    }
}