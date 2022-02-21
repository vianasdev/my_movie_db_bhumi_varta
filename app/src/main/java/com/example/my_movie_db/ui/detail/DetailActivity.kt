package com.example.my_movie_db.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.my_movie_db.R
import com.example.my_movie_db.fragment.detail.DetailFragment

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        replaceFragmentWithBundle(
            DetailFragment(),
            DetailFragment.MOVIE_ID,
            intent.getStringExtra(DetailFragment.MOVIE_ID)!!
        )
    }

    private fun replaceFragmentWithBundle(fragment: Fragment, key: String, item: String) {
        val bundle = Bundle()
        bundle.putString(key, item)

        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle

        transaction.replace(R.id.detail_fragment, fragment)
        transaction.commit()
    }
}