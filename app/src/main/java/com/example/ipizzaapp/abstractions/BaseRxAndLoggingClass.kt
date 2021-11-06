package com.example.ipizzaapp.abstractions

import android.util.Log
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import io.reactivex.disposables.CompositeDisposable

abstract class BaseRxAndLoggingClass {
    private val isLoggingEnable = false

    protected fun log (throwable: Throwable) {
        if (isLoggingEnable)
            Log.e(
                "RxError",
                "Problem:\n\n$throwable")
    }

    protected val compositeDisposable = CompositeDisposable()

    fun clearDisposable () {
        compositeDisposable.clear()
    }
}