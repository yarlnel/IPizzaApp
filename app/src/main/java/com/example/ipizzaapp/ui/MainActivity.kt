package com.example.ipizzaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.root.RootFragment
import com.example.ipizzaapp.utils.custom_managers.setup.SetupDataManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var setupDataManager: SetupDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IPizzaApp)


        setupDataManager.setupAllToDb()

        setContentView(R.layout.activity_main)

        Router.setAppActivity(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.root, RootFragment.newInstance())
            .addToBackStack(null)
            .commit()
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