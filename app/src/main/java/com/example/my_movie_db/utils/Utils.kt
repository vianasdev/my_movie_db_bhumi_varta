package com.example.my_movie_db.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object Utils {

    fun hideSoftKeyboard(fragment: Fragment) {
        val inputMethodManager =
            fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(fragment.requireActivity().currentFocus?.windowToken, 0)
    }

}