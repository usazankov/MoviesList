package ru.sample.movies.presentation.view.presenter

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage
import ru.sample.movies.domain.executor.PostExecutionThread
import ru.sample.movies.domain.executor.ThreadExecutor
import ru.sample.movies.domain.interactor.GetMoviesPage
import ru.sample.movies.presentation.view.interfaces.SelectMoviesDataView
import ru.sample.movies.presentation.view.utils.Constant.*
import javax.inject.Inject

@InjectViewState
class SelectMoviesPresenter : BasePresenter<SelectMoviesDataView>() {

    @Inject
    lateinit var getMoviesPage: GetMoviesPage

    @Inject
    lateinit var threadExecutor: ThreadExecutor

    @Inject
    lateinit var postExecutionThread: PostExecutionThread

    val compositeDisposable = CompositeDisposable();

    init {
        component.inject(this)
    }

    /**
     * Initializes the presenter.
     */
    fun initialize() {
        // Configures how a PagedList loads content from the MovieDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            // Size hint for initial load of PagedList
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
            // Size of each page loaded by the PagedList
            .setPageSize(PAGE_SIZE)
            // Prefetch distance which defines how far ahead to load
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()
        val pagedList = PagedList.Builder(dataSource, config)
            .setNotifyExecutor(threadExecutor)
            .setFetchExecutor(threadExecutor)
            .build()
        viewState.renderMoviesList(pagedList)
    }

    var dataSource: PositionalDataSource<Movie> = object : PositionalDataSource<Movie>(){
        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Movie>) {
            Log.d("PagingLib", "loadRange, startPosition = " + params.startPosition +
                    ", loadSize = " + params.loadSize);
            //TODO
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Movie>) {
            Log.d("PagingLib", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                    ", requestedLoadSize = " + params.requestedLoadSize);
            val d = getMoviesPage.buildUseCaseObservable(GetMoviesPage.Params.forPage(1))
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe {
                    callback.onResult(it.results, 0)
                }
            compositeDisposable.add(d)
        }

    }

    /**
     * Loads all users.
     */
//    private fun loadMoviesList() {
//        this.hideViewRetry()
//        this.showViewLoading()
//        getMoviesPage.execute(GetMoviesListObserver(), null)
//    }

    private fun hideViewRetry() {
        viewState.hideRetry()
    }

    private fun showViewLoading() {
        viewState.showLoading()
    }

    private fun hideViewLoading() {
        viewState.hideLoading()
    }

//    fun selectBank(shortBankEntity: ShortBankEntity){
//        viewState.viewBankDetails(shortBankEntity)
//    }

    private fun showViewRetry(throwable: Throwable) {
        //val message = ErrorMessageFactory.create(throwable)
        viewState.showRetry("Ошибка")
    }

    override fun onDestroy() {
        super.onDestroy()
        getMoviesPage.dispose()
        compositeDisposable.dispose()
    }

//    private inner class GetMoviesListObserver : DisposableObserver<MoviesPage>() {
//
//        override fun onComplete() {
//            hideViewLoading()
//        }
//
//        override fun onError(e: Throwable) {
//            hideViewLoading()
//            showViewRetry(e)
//        }
//
//        override fun onNext(moviesPage: MoviesPage) {
//            viewState.renderMoviesPage(moviesPage)
//        }
//    }
}