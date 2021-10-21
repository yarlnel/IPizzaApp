package com.example.ipizzaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.ipizzaapp.R
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.home.HomeFragment
import com.example.ipizzaapp.ui.root.RootFragment

class MainActivity : AppCompatActivity() {
    val router = Router(R.id.root, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.root, RootFragment.newInstance())
            .addToBackStack("root")
            .commit()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.fragments.last().tag == HomeFragment.TAG) {
            supportFragmentManager.popBackStack()
            supportFragmentManager.popBackStack()
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }

    }
}