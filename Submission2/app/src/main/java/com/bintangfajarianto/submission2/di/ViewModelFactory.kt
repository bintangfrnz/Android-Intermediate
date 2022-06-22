package com.bintangfajarianto.submission2.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bintangfajarianto.submission2.ui.authentication.login.LoginViewModel
import com.bintangfajarianto.submission2.ui.authentication.register.RegisterViewModel
import com.bintangfajarianto.submission2.ui.home.HomeViewModel
import com.bintangfajarianto.submission2.ui.publish.PublishViewModel
import com.bintangfajarianto.submission2.ui.splash.SplashViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(
                    Injection.provideAuthRepository(context),
                    Injection.provideStoryRepository(context)
                ) as T
            }
            modelClass.isAssignableFrom(PublishViewModel::class.java) -> {
                return PublishViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}