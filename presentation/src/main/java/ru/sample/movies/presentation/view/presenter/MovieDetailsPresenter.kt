package ru.sample.movies.presentation.view.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.observers.DisposableObserver
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.executor.PostExecutionThread
import ru.sample.movies.domain.executor.ThreadExecutor
import ru.sample.movies.domain.interactor.GetMovieDetails
import ru.sample.movies.presentation.view.interfaces.MovieDetailsView
import javax.inject.Inject

@InjectViewState
class MovieDetailsPresenter : BasePresenter<MovieDetailsView>() {

    @Inject
    lateinit var getMoviesDetails: GetMovieDetails

    @Inject
    lateinit var threadExecutor: ThreadExecutor

    @Inject
    lateinit var postExecutionThread: PostExecutionThread

    init {
        component.inject(this)
    }

    fun initialize(movieId: Int, syncWithHostOnly: Boolean){
        viewState.hideError()
        showViewLoading()
        getMoviesDetails.execute(GetMoviesListObserver(), GetMovieDetails.Params.forMovieId(movieId, syncWithHostOnly))
    }

    override fun onDestroy() {
        super.onDestroy()
        getMoviesDetails.dispose()
    }

    private fun showViewLoading() {
        viewState.showLoading()
    }

    private fun hideViewLoading() {
        viewState.hideLoading()
    }

    private inner class GetMoviesListObserver : DisposableObserver<Movie>() {

        override fun onComplete() {
            hideViewLoading()
        }

        override fun onError(e: Throwable) {
            hideViewLoading()
            viewState.showError("Ошибка")
        }

        override fun onNext(movie: Movie) {
            viewState.renderMovie(movie)
        }
    }
}