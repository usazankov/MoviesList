package ru.sample.movies.domain.repository

import io.reactivex.Observable
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage

interface IMoviesRepository {

    /**
     * @return page of movies
     * @param page - page number ranging from 1
     */
    fun listMovies(page: Int): Observable<MoviesPage>

    /**
     * @return description of movie
     * @param id - movie id
     */
    fun movieDescription(id: Int): Observable<Movie>
}