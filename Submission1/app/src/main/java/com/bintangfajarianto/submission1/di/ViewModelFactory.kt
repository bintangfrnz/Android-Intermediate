package com.bintangfajarianto.submission1.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bintangfajarianto.submission1.data.repository.Repository
import com.bintangfajarianto.submission1.ui.authentication.login.LoginViewModel
import com.bintangfajarianto.submission1.ui.authentication.register.RegisterViewModel
import com.bintangfajarianto.submission1.ui.home.HomeViewModel
import com.bintangfajarianto.submission1.ui.publish.PublishViewModel
import com.bintangfajarianto.submission1.ui.splash.SplashViewModel

class ViewModelFactory private constructor(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PublishViewModel::class.java) -> {
                return PublishViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}