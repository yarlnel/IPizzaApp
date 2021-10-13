package com.example.ipizzaapp.fragment_lib

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ipizzaapp.ui.home.HomeFragment
import java.lang.Exception


class Router(
    private val rootFragmentContainerId: Int,
    private val appActivity: AppCompatActivity,
    private val configLambda: Router.() -> Unit = {},
) {
    private val isLoggingEnable = true

    private fun log (s: String) {
        Log.e(Router::class.java.simpleName, s)
    }

    private val fragmentManager = appActivity.supportFragmentManager

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

        fragmentManager
            .beginTransaction()
            .add(rootFragmentContainerId, fragment, screenKey)
            .addToBackStack(null)
            .commit()

        currentFragment = fragment

        return this
    }


    fun back(): Router {
        if (fragmentManager.fragments.map{ it.tag }.last() == HomeFragment.TAG)
            appActivity.onBackPressed()
        fragmentManager.popBackStack()
        return this
    }

    fun backTo(screenKey: String): Router {
        val frag  =  fragmentManager.findFragmentByTag(screenKey) ?: throw Exception("""
            Fragment with tag: $screenKey not find !!!
        """.trimIndent())

        val elemIndex = fragmentManager.fragments.map { it.tag }.indexOf(screenKey)
        val fragmentsCount = fragmentManager.fragments.count()
        for (i in 2 .. (fragmentsCount - elemIndex)) fragmentManager.popBackStack()

        return this
    }

    fun replace(screenKey: String, fragment: Fragment): Router {
        fragmentManager.beginTransaction().replace(rootFragmentContainerId, fragment, screenKey)
        currentFragment = fragment
        return this
    }

}



