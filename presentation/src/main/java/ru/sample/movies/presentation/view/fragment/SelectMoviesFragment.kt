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
import ru.sample.movies.presentation.view.adapter.GridSpacingItemDecoration
import ru.sample.movies.presentation.view.adapter.MoviePagedListAdapter
import ru.sample.movies.presentation.view.interfaces.SelectMoviesDataView
import ru.sample.movies.presentation.view.presenter.SelectMoviesPresenter
import ru.sample.movies.presentation.view.utils.Constant.*
import ru.sample.presentation.internal.di.components.MoviesComponent
import javax.inject.Inject

class SelectMoviesFragment : BaseFragment(), SelectMoviesDataView {

    private var moviesListListener: MoviesListListener? = null

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var selectMoviesPresenter: SelectMoviesPresenter


//    private val itemClickListener = object : BanksAdapter.OnItemClickListener {
//        override fun onBankItemClicked(shortBankEntity: ShortBankEntity) {
//            selectBankPresenter.selectBank(shortBankEntity)
//        }
//    }

    @Inject
    lateinit var moviePagedListAdapter: MoviePagedListAdapter

    init {
        retainInstance = true
    }

    interface MoviesListListener {
        fun onMovieClicked(movie: Movie)
    }

    override fun onClickRetry() {
        selectMoviesPresenter.initialize()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.moviesListListener = activity as? MoviesListListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getComponent(MoviesComponent::class.java)!!.inject(this)
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
        initialize()
    }

    private fun initialize() {
        selectMoviesPresenter.initialize()
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

    fun context(): Context {
        return this.activity!!.getApplicationContext()
    }

}