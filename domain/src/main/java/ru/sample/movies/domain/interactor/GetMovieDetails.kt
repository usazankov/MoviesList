package ru.sample.movies.domain.interactor

import io.reactivex.Observable
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.executor.PostExecutionThread
import ru.sample.movies.domain.executor.ThreadExecutor
import ru.sample.movies.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    val moviesRepository: IMoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread) : UseCase<Movie, GetMovieDetails.Params?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<Movie> {
        return moviesRepository.movieDescription(params?.id ?: -1, params?.syncWithOnlyHost ?: false)
    }

    class Params private constructor(val id: Int, val syncWithOnlyHost: Boolean) {
        companion object {
            fun forMovieId(id: Int, syncWithOnlyHost: Boolean): Params {
                return Params(id, syncWithOnlyHost)
            }
        }
    }
}