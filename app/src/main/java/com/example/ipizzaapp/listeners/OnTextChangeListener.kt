package com.example.ipizzaapp.listeners

import android.text.Editable
import android.text.TextWatcher

class OnTextChangeListener
    (private val onTextChange: (text: String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChange(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }

}