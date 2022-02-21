package com.example.my_movie_db.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.my_movie_db.R
import com.example.my_movie_db.fragment.detail.DetailFragment
import com.example.my_movie_db.fragment.favorite.FavoriteFragment
import com.example.my_movie_db.fragment.home.HomeFragment
import com.example.my_movie_db.fragment.location.LocationFragment
import com.example.my_movie_db.fragment.popular.PopularFragment
import com.example.my_movie_db.models.MovieModel
import com.example.my_movie_db.ui.detail.DetailActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = ""

        replaceFragment(HomeFragment())

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.setOnNavigationItemSelectedListener {
            toggleBottomBarMenu(it)
            when (it.itemId) {
                R.id.bnb_home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.bnb_popular -> {
                    replaceFragment(PopularFragment())
                }
                R.id.bnb_favorite -> {
                    replaceFragment(FavoriteFragment())
                }
                R.id.bnb_location -> {
                    replaceFragment(LocationFragment())
                }
            }
            true
        }
    }

    private fun toggleBottomBarMenu(item: MenuItem) {
        val items = listOf(R.id.bnb_home, R.id.bnb_popular, R.id.bnb_favorite, R.id.bnb_location)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        for (menuItem in items) {
            bottomNavBar.menu.findItem(menuItem).isEnabled = item.itemId != menuItem
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment)
        transaction.commit()
    }

    fun navToDetail(movieId:String) {
        startActivity(Intent(this, DetailActivity::class.java).putExtra(DetailFragment.MOVIE_ID, movieId))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}