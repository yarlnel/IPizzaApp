package com.example.ipizzaapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ipizzaapp.di.AppComponent
import com.example.ipizzaapp.di.AppContextModule
import com.example.ipizzaapp.di.DaggerAppComponent
import com.example.ipizzaapp.fragment_lib.Router
import dagger.Component

class MainApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .appContextModule(AppContextModule(applicationContext))
            .build()
        super.onCreate()
    }
}

val Context.appComponent : AppComponent
    get () = when (this) {
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }

val Fragment.appComponent : AppComponent
    get () = requireActivity().appComponent