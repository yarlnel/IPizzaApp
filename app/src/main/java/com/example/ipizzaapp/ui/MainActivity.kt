package com.example.ipizzaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.root.RootFragment

class MainActivity : AppCompatActivity() {
    val router = Router(R.id.root, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IPizzaApp)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .add(R.id.root, RootFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun parentBackPressedFunction() = super.onBackPressed()

    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.last()
        if (lastFragment is BackPressedStrategyOwner) {
            lastFragment.customBackPressedHandlerFunction()
        } else {
            supportFragmentManager.popBackStack()
        }

    }
}