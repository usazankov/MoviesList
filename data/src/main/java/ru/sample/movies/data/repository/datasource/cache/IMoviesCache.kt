package ru.sample.movies.data.repository.datasource.cache

import io.reactivex.Observable
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage

interface IMoviesCache : ICache {

    /**
     * Gets an [Observable] which will emit a [Movie].
     *
     * @param movieId The bank id to retrieve data.
     */
    fun getMovie(movieId: Int?): Observable<Movie>

    /**
     * Gets an [Observable] which will emit a [MoviesPage].
     *
     */
    fun getMoviesPage(page: Int?): Observable<MoviesPage>

    /**
     * Puts Movies Page element into the cache.
     *
     * @param moviesPage Element to insert in the cache.
     */
    fun putMoviesPage(moviesPage: MoviesPage)

    /**
     * Puts Movie element into the cache.
     *
     * @param movie Element to insert in the cache.
     */
    fun putMovie(movie: Movie)

    /**
     * Checks if an element (Movie) exists in the cache.
     *
     * @param movieId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    fun isCachedMovie(movieId: Int?): Boolean

    /**
     * Checks if an element (Movies Page) exists in the cache.
     *
     * @return true if the element is cached, otherwise false.
     */
    fun isCachedMoviesPage(page: Int?): Boolean

}