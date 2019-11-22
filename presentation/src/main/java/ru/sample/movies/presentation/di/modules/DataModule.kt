package ru.sample.movies.presentation.di.modules

import dagger.Module
import dagger.Provides
import ru.sample.movies.data.repository.MoviesRepository
import ru.sample.movies.data.repository.datasource.cache.IMoviesCache
import ru.sample.movies.data.repository.datasource.cache.MoviesCacheImpl
import ru.sample.movies.domain.repository.IMoviesRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMoviesCache(moviesCache: MoviesCacheImpl): IMoviesCache {
        return moviesCache
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesRepository: MoviesRepository): IMoviesRepository {
        return moviesRepository
    }
}