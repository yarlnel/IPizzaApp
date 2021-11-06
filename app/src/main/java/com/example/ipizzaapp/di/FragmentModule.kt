package com.example.ipizzaapp.di

import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.details.DetailsDialogFragment
import com.example.ipizzaapp.ui.home.HomeFragment
import com.example.ipizzaapp.ui.ordered_pizza.PlaceOrderFragment
import com.example.ipizzaapp.ui.preview.PreviewFragment
import com.example.ipizzaapp.ui.root.RootFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {
    @ContributesAndroidInjector
    fun homeFragment () : HomeFragment

    @ContributesAndroidInjector
    fun detailsDialogFragment () : DetailsDialogFragment

    @ContributesAndroidInjector
    fun cartFragment () : CartFragment

    @ContributesAndroidInjector
    fun placeOrderFragment () : PlaceOrderFragment

    @ContributesAndroidInjector
    fun rootFragment () : RootFragment

    @ContributesAndroidInjector
    fun previewFragment () : PreviewFragment

    @ContributesAndroidInjector
    fun mainActivity () : MainActivity
}