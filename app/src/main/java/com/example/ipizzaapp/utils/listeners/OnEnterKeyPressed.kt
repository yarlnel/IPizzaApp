package com.example.ipizzaapp.utils.listeners

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView


class OnEnterKeyPressed
    (private val callbackLambda: (text: String) -> Unit)
    : TextView.OnEditorActionListener {
        override fun onEditorAction
                    (v: TextView?,
                     actionId: Int,
                     event: KeyEvent?): Boolean
            = when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        callbackLambda(v?.text.toString())
                        true
                    }
                    else -> false
                }

}