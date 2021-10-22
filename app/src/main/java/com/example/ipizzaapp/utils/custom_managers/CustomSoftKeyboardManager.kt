package com.example.ipizzaapp.utils.custom_managers

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CustomSoftKeyboardManager
    (private val activity: AppCompatActivity) {
    private val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
                as? InputMethodManager

    fun hideKeyboard() {
        inputMethodManager?.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_IMPLICIT_ONLY,
        )
    }

    fun showKeyboardAndSetFocus (focusEditText: EditText) {
        focusEditText.requestFocus()
        inputMethodManager?.showSoftInput(
            focusEditText,
            InputMethodManager.SHOW_IMPLICIT,
        )
    }
}