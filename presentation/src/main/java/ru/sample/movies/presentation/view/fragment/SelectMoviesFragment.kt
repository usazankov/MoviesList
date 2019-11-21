package ru.sample.movies.presentation.view.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_select_movie.*
import ru.sample.movies.R
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.presentation.di.components.MoviesComponent
import ru.sample.movies.presentation.view.adapter.GridSpacingItemDecoration
import ru.sample.movies.presentation.view.adapter.MoviePagedListAdapter
import ru.sample.movies.presentation.view.interfaces.SelectMoviesDataView
import ru.sample.movies.presentation.view.presenter.SelectMoviesPresenter
import ru.sample.movies.presentation.view.utils.Constant.*
import javax.inject.Inject

class SelectMoviesFragment : BaseFragment(), SelectMoviesDataView {

    private var moviesListListener: MoviesListListener? = null

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var selectMoviesPresenter: SelectMoviesPresenter

    @Inject
    lateinit var moviePagedListAdapter: MoviePagedListAdapter

    var bundle: Bundle? = null

    interface MoviesListListener {
        fun onMovieClicked(movie: Movie)
    }

    override fun onClickRetry() {
        selectMoviesPresenter.initialize(false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.moviesListListener = activity as? MoviesListListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getComponent(MoviesComponent::class.java)!!.inject(this)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView = inflater.inflate(R.layout.fragment_select_movie, container, false)
        initToolBar(fragmentView, inflater)
        initProgressBar(fragmentView)
        //setTitleToolBar(R.string.title_list_banks)

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        if(bundle == null){
            bundle = Bundle()
            initialize()
        }
    }

    private fun initialize() {
        selectMoviesPresenter.initialize(false)
    }

    override fun renderMoviesList(pagedList: PagedList<Movie>){
        moviePagedListAdapter.submitList(pagedList)
    }

    override fun viewMoviesDetails(movie: Movie) {

    }

    private fun setupViews() {
        val layoutManager = GridLayoutManager(context(), GRID_SPAN_COUNT)
        // Set the layout manager to the RecyclerView
        rv_movie.setLayoutManager(layoutManager)
        val decoration = GridSpacingItemDecoration(
            GRID_SPAN_COUNT, GRID_SPACING, GRID_INCLUDE_EDGE
        )
        rv_movie.addItemDecoration(decoration)
        // Use this setting to improve performance if you know that changes in content do not
        // change the child layout size in the RecyclerView
        rv_movie.setHasFixedSize(true)
        rv_movie.setAdapter(moviePagedListAdapter)

        swipe_refresh.setOnRefreshListener { selectMoviesPresenter.initialize(true) }

        moviePagedListAdapter.mOnClickHandler = object : MoviePagedListAdapter.MoviePagedListAdapterOnClickHandler {
            override fun onItemClick(movie: Movie) {
                moviesListListener?.onMovieClicked(movie)
            }
        }
    }

    override fun showLoading() {
        showProgressBarLoading()
    }

    override fun hideLoading() {
        hideProgressBarLoading()
    }

    override fun showRetry(message: String) {
        //showErrorPopupRtry(message)
    }

    override fun hideRetry() {
        //hideErrorPopupRetry()
    }

    override fun showError(message: String) {
        //showErrorPopup(message, null)
    }

    override fun hideRefresh() {
        swipe_refresh.isRefreshing = false
    }

    fun context(): Context {
        return this.activity!!.getApplicationContext()
    }

}