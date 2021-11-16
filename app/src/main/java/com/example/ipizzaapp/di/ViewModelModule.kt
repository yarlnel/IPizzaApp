package com.example.ipizzaapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ipizzaapp.ui.cart.CartViewModel
import com.example.ipizzaapp.ui.details.DetailsViewModel
import com.example.ipizzaapp.ui.home.HomeViewModel
import com.example.ipizzaapp.ui.preview.PreviewViewModel
import com.example.ipizzaapp.utils.factories.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import kotlin.reflect.KClass

@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(HomeViewModel::class)]
    fun bindHomeViewModel (homeViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(PreviewViewModel::class)]
    fun bindPreviewViewModel (previewViewModel: PreviewViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CartViewModel::class)]
    fun bindCartViewModel(cartViewModel: CartViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}



@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)