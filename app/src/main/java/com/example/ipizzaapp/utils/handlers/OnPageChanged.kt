package com.example.ipizzaapp.utils.handlers

import androidx.viewpager2.widget.ViewPager2

class OnPageSelected
    (private val callbackLambda: (pageNumber: Int) -> Unit)
    : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        callbackLambda(position + 1)
        super.onPageSelected(position)
    }
}