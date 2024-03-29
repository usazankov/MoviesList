package ru.sample.movies.presentation.view.presenter

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.sample.movies.domain.entity.Movie
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

    val compositeDisposable = CompositeDisposable()

    private val dataSource = MoviesPositionalDataSource(false)

    init {
        component.inject(this)
    }

    /**
     * Initializes the presenter.
     * @param syncWithHost
     * true - data will be taken only from the host,
     * false - data will be taken if possible from the cache
     */
    fun initialize(syncWithHost: Boolean) {
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

        dataSource.syncWithOnlyHost = syncWithHost

        val pagedList = PagedList.Builder(dataSource, config)
            .setNotifyExecutor(threadExecutor)
            .setFetchExecutor(threadExecutor)
            .build()
        viewState.hideError()
        viewState.showLoading()
        viewState.renderMoviesList(pagedList)
    }

    inner class MoviesPositionalDataSource(var syncWithOnlyHost: Boolean): PositionalDataSource<Movie>(){
        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Movie>) {
            Log.d("PagingLib", "loadRange, startPosition = " + params.startPosition +
                    ", loadSize = " + params.loadSize)
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Movie>) {
            Log.d("PagingLib", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                    ", requestedLoadSize = " + params.requestedLoadSize)
            val d = getMoviesPage.buildUseCaseObservable(GetMoviesPage.Params.forPage(1, syncWithOnlyHost))
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .doOnError {
                    viewState.showError("Ошибка")
                    viewState.hideLoading()
                }
                .subscribe {
                    callback.onResult(it.results, 0)
                    viewState.hideLoading()
                }
            compositeDisposable.add(d)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getMoviesPage.dispose()
        compositeDisposable.dispose()
    }


}