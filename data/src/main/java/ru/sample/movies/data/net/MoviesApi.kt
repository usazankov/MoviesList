package ru.sample.movies.data.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sample.movies.domain.entity.*

interface MoviesApi {

    @GET("/LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/movies.json")
    fun moviesList(): Observable<MoviesPage>

}