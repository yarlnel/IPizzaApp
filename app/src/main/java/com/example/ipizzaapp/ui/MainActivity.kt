package com.example.ipizzaapp.ui

import android.os.Bundle
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.root.RootFragment
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable

class MainActivity : DaggerAppCompatActivity() {


    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_IPizzaApp)

        setContentView(R.layout.activity_main)

        Router.setAppActivity(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.root, RootFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun parentBackPressedFunction() = super.onBackPressed()

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isEmpty())
            super.onBackPressed()

        val lastFragment = supportFragmentManager.fragments.last()
        if (lastFragment is BackPressedStrategyOwner) {
            lastFragment.customBackPressedHandlerFunction()
        } else {
            supportFragmentManager.popBackStack()
        }

    }
}