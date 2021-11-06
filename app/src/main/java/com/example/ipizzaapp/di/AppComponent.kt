package com.example.ipizzaapp.di

import com.example.ipizzaapp.MainApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<MainApp>{

    @Component.Builder
    interface Builder {
        fun appContextModule (appContextModule: AppContextModule) : Builder

        @BindsInstance
        fun bindContext(mainApp: MainApp) : Builder

        fun build() : AppComponent
    }

}