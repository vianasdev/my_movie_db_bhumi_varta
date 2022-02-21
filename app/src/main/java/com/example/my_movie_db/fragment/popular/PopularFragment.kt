package com.example.my_movie_db.fragment.popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.my_movie_db.databinding.FragmentPopularBinding
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.ui.main.MainActivity
import com.example.my_movie_db.utils.EventObserver
import com.example.my_movie_db.utils.Utils
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PopularFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PopularViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentPopularBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = viewModel

        observeViewModel()
        setupSearchBar()

        return viewDataBinding.root
    }

    override fun onResume() {
        viewModel.getMovies()
        super.onResume()
    }

    private fun observeViewModel() {
        viewModel.moviesEvent.observe(viewLifecycleOwner, EventObserver {
            setupMovieAdapter(it)
        })

        viewModel.navToDetailEvent.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).navToDetail(it.id)
        })
    }

    private fun setupMovieAdapter(banners: List<MovieModel>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = MoviesAdapter(viewModel, banners)
            viewDataBinding.rvMovies.adapter = adapter
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearchBar() {
        viewDataBinding.etSearch.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= viewDataBinding.etSearch.right - viewDataBinding.etSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    onSearch()
                    return@OnTouchListener true
                }
            }
            false
        })

        viewDataBinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    onSearch()
                    return true
                }
                return false
            }
        })

    }

    private fun onSearch() {
        viewDataBinding.llSearchText.isVisible = true
        viewDataBinding.tvSearchText.text = "'${viewDataBinding.etSearch.text}'"
        viewModel.search(viewDataBinding.etSearch.text.toString().trim())
        Utils.hideSoftKeyboard(this@PopularFragment)
    }
}