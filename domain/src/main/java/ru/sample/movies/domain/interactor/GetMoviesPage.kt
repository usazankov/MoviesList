package ru.sample.movies.domain.interactor

import io.reactivex.Observable
import ru.sample.movies.domain.entity.MoviesPage
import ru.sample.movies.domain.executor.PostExecutionThread
import ru.sample.movies.domain.executor.ThreadExecutor
import ru.sample.movies.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetMoviesPage @Inject constructor(
    val moviesRepository: IMoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread) : UseCase<MoviesPage, GetMoviesPage.Params?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<MoviesPage> {
        return moviesRepository.listMovies(params?.page ?: 0, params?.syncWithHost ?: false)
    }

    class Params private constructor(val page: Int, val syncWithHost: Boolean) {
        companion object {
            fun forPage(page: Int, syncWithHost: Boolean): Params {
                return Params(page, syncWithHost)
            }
        }
    }
}