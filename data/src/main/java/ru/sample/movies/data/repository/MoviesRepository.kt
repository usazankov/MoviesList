package ru.sample.movies.data.repository

import io.reactivex.Observable
import ru.sample.movies.data.repository.datasource.MoviesDataStoreFactory
import ru.sample.movies.domain.entity.*
import ru.sample.movies.domain.repository.IMoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(val bankDataStoreFactory: MoviesDataStoreFactory) :
    IMoviesRepository {

    override fun listMovies(page: Int, syncWithHost: Boolean): Observable<MoviesPage> {
        if(syncWithHost){
            return bankDataStoreFactory.createCloudMoviesPage(page).listMovies(page)
        }
        return bankDataStoreFactory.createWithCacheMoviesPage(page).listMovies(page)
    }

    override fun movieDescription(id: Int, syncWithHost: Boolean): Observable<Movie> {
        if(syncWithHost){
            return bankDataStoreFactory.createCloudDetailsMovie(id).moviesDescription(id)
        }
        return bankDataStoreFactory.createWithCacheDetailsMovie(id).moviesDescription(id)
    }

}