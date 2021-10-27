package com.example.ipizzaapp.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PicassoModule {
    @Provides @Singleton
    fun providePicasso () : Picasso
        = Picasso.get()
}