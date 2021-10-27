package com.example.ipizzaapp.ui.preview

import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.pojo.Pizza
import com.google.android.material.appbar.AppBarLayout
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PreviewViewModel : ViewModel() {
    private val _currentPageNumber
        = BehaviorSubject.create<Int>().apply {
            onNext(1)
        }

    val currentPageNumber : Observable<Int> = _currentPageNumber

    fun setCurrentPageNumber (pageNumber: Int) {
        _currentPageNumber.onNext(pageNumber)
    }
}