package ru.sample.movies.presentation.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sample.movies.data.executor.JobExecutor
import ru.sample.movies.domain.executor.PostExecutionThread
import ru.sample.movies.domain.executor.ThreadExecutor
import ru.sample.movies.presentation.AndroidApplication
import ru.sample.movies.presentation.UIThread
import javax.inject.Singleton

@Module
class ApplicationModule(val androidApplication: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = androidApplication.applicationContext

    @Provides
    @Singleton
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @Singleton
    internal fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

}