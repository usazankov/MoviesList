package ru.sample.movies.data.repository.datasource

import io.reactivex.Observable
import ru.sample.movies.data.net.MoviesApi
import ru.sample.movies.data.repository.datasource.cache.IMoviesCache
import ru.sample.movies.domain.entity.*
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

interface IMovieDataStore{
    fun listMovies(page: Int?): Observable<MoviesPage>
    fun moviesDescription(movieId: Int): Observable<Movie>
}

@Singleton
class MoviesDataStoreFactory @Inject constructor(
    private val api: MoviesApi,
    private val moviesCache: IMoviesCache
) {

    /**
     * Create [IMovieDataStore] from a movie id.
     */
    fun createWithCacheDetailsMovie(movieId: Int): IMovieDataStore {
        val bankDataStore: IMovieDataStore

        if (!this.moviesCache.isExpired() && this.moviesCache.isCachedMovie(movieId)) {
            bankDataStore =
                DiskMoviesDataStore(this.moviesCache)
        } else {
            bankDataStore =
                CloudMoviesDataStore(api, moviesCache)
        }
        return bankDataStore
    }

    /**
     * Create [IMovieDataStore] from a page.
     */
    fun createWithCacheMoviesPage(page: Int): IMovieDataStore {
        val bankDataStore: IMovieDataStore

        if (!this.moviesCache.isExpired() && this.moviesCache.isCachedMoviesPage(page)) {
            bankDataStore =
                DiskMoviesDataStore(this.moviesCache)
        } else {
            bankDataStore =
                CloudMoviesDataStore(api, moviesCache)
        }
        return bankDataStore
    }

    /**
     * Create [IMovieDataStore] from a page.
     */
    fun createCloudMoviesPage(page: Int) = CloudMoviesDataStore(api, moviesCache)

    /**
     * Create [IMovieDataStore] from a page.
     */
    fun createCloudDetailsMovie(movieId: Int) = CloudMoviesDataStore(api, moviesCache)
}

class CloudMoviesDataStore @Inject constructor(private val api: MoviesApi, private val moviesCache: IMoviesCache) :
    IMovieDataStore {
    override fun listMovies(page: Int?): Observable<MoviesPage> {
        return api.moviesList()
            .doOnNext {

                //save movies list to cache
                moviesCache.putMoviesPage(it)

                //save movie details to cache
                for(item in it.results){
                    moviesCache.putMovie(item)
                }
            }
    }

    override fun moviesDescription(movieId: Int): Observable<Movie> {
        return api.moviesList()
            .map {
                for(item in it.results){
                    if(item.id == movieId) return@map item
                }
                throw RuntimeException("movie id: ${movieId} not found")
            }
    }
}

class DiskMoviesDataStore(
    private val moviesCache: IMoviesCache) : IMovieDataStore {

    override fun listMovies(page: Int?): Observable<MoviesPage> {
        return moviesCache.getMoviesPage(page)
    }

    override fun moviesDescription(movieId: Int): Observable<Movie> {
        return moviesCache.getMovie(movieId)
    }

}