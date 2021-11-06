package com.example.ipizzaapp.utils.custom_managers.keyboard

import androidx.appcompat.app.AppCompatActivity
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CustomKeyboardManagerFactory {
    fun create(activity: AppCompatActivity) : CustomSoftKeyboardManager
}