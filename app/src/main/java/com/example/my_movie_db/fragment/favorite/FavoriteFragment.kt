package com.example.my_movie_db.fragment.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.my_movie_db.databases.favorite.Favorite
import com.example.my_movie_db.databinding.FragmentFavoriteBinding
import com.example.my_movie_db.ui.main.MainActivity
import com.example.my_movie_db.utils.EventObserver
import com.example.my_movie_db.utils.Utils
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FavoriteViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentFavoriteBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = viewModel

        observeViewModel()
        setupSearchBar()

        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovies()
    }

    private fun observeViewModel() {
        viewModel.favoritesEvent.observe(viewLifecycleOwner, EventObserver {
            setupFavoriteAdapter(it)
        })

        viewModel.navToDetailEvent.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).navToDetail(it.id)
        })
    }

    private fun setupFavoriteAdapter(items: List<Favorite>) {
        val viewModel = viewDataBinding.viewModel
        viewModel?.let {
            val adapter = FavoriteAdapter(viewModel, items)
            viewDataBinding.rvFavorite.adapter = adapter
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
        Utils.hideSoftKeyboard(this@FavoriteFragment)
    }
}