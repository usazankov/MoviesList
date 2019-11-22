package ru.sample.movies.presentation.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sample.movies.data.net.MoviesApi

@Module(includes = arrayOf(NetModule::class))
class RestModule {

    @Provides
    fun provideSoftPosApi(retrofit: Retrofit) = retrofit.create(MoviesApi::class.java)
}