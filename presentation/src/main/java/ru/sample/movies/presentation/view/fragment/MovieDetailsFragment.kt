package ru.sample.movies.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.sample.movies.R
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.presentation.di.components.MoviesComponent
import ru.sample.movies.presentation.view.interfaces.MovieDetailsView
import ru.sample.movies.presentation.view.presenter.MovieDetailsPresenter
import ru.sample.movies.presentation.view.utils.UIParam

class MovieDetailsFragment : BaseFragment(), MovieDetailsView{

    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var movieDetailsPresenter: MovieDetailsPresenter

    init {
        retainInstance = true
    }

    companion object{
        fun forMovieId(id: Int): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt(UIParam.STATE_PARAM_BANK_ID, id)
            movieDetailsFragment.arguments = args
            return movieDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getComponent(MoviesComponent::class.java)!!.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView = inflater.inflate(R.layout.fragment_details_movie, container, false)
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

    private fun setupViews() {

    }

    private fun initialize() {
        movieDetailsPresenter.initialize(false)
    }

    override fun renderMovie(movie: Movie) {

    }

    override fun showLoading() {
        showProgressBarLoading()
    }

    override fun hideLoading() {
        hideProgressBarLoading()
    }

    override fun showRetry(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRetry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRefresh() {

    }

    override fun onClickRetry() {
        movieDetailsPresenter.initialize(false)
    }

    fun context(): Context {
        return this.activity!!.getApplicationContext()
    }

}