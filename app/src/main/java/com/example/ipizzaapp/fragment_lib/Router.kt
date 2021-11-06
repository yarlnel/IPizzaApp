package com.example.ipizzaapp.fragment_lib

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ipizzaapp.R
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment
import com.google.gson.Gson
import java.lang.Exception
import java.util.*
import javax.inject.Inject


object Router {
    private var _appActivity: MainActivity? = null
    fun setAppActivity(mainActivity: MainActivity) {
        _appActivity = mainActivity
    }

    private val tags = mutableListOf<String>()

    private val appActivity: MainActivity
        get() = _appActivity!!
    private const val isLoggingEnable = true
    private const val rootFragmentContainerId: Int = R.id.root


    private fun log(s: String) {
        Log.e(Router::class.java.simpleName, s)
    }

    private val fragmentManager by lazy {
        appActivity.supportFragmentManager
    }

    private var currentFragment: Fragment? = null


    fun clearBackStack(): Router {
        if (fragmentManager.backStackEntryCount > 0) {
            val first = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStack(
                first.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        return this
    }

    fun from (fragment: Fragment) : Router {
        currentFragment = fragment
        return this
    }

    fun goTo(screenKey: String, fragment: Fragment): Router {
        currentFragment ?: throw Exception(
            """
                |We haven't info about current fragment !!!
                |Use from() become using goTo() 
                """.trimMargin("|")
        )
        tags += screenKey
        fragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(rootFragmentContainerId, fragment, screenKey)
            .addToBackStack(screenKey)
            .commit()


        currentFragment = fragment

        return this
    }


    fun back(): Router {
        fragmentManager.popBackStack()
        tags.removeLast()
        return this
    }

    fun backTo(screenKey: String): Router {
        if (!tags.contains(screenKey))
            throw Exception("""
                Router cant backTo $screenKey
                Because Fragment with this screenKey 
                Not contains in Router local backstack
                Use goTo with this ($screenKey) screenKey
                Become using backTo ($screenKey)
            """.trimIndent())

        repeat((tags.size - 1) - tags.indexOf(screenKey)) {
            tags.removeLast()
            fragmentManager.popBackStack()
        }
        return this
    }

    fun replace(screenKey: String, fragment: Fragment): Router {
        tags[tags.size - 1] = screenKey
        fragmentManager
            .beginTransaction()
            .replace(rootFragmentContainerId, fragment, screenKey)
            .commit()
        currentFragment = fragment
        return this
    }

}



