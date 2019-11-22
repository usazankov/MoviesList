package ru.sample.movies.presentation.di.modules

import dagger.Module

@Module(includes = arrayOf(DataModule::class, PicassoModule::class, RestModule::class))
class MoviesModule {
}