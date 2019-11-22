package ru.sample.movies.presentation

import android.app.Application
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins
import ru.sample.movies.presentation.di.components.ApplicationComponent
import ru.sample.movies.presentation.di.components.DaggerApplicationComponent
import ru.sample.movies.presentation.di.modules.ApplicationModule

class AndroidApplication : Application() {

    companion object{
        lateinit var application: AndroidApplication
    }

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        application = this
        RxJavaPlugins.setErrorHandler {
            Log.d("RX", "error: " + it.message)
            it.printStackTrace()
        }
        initializeInjector()
    }

    private fun initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}