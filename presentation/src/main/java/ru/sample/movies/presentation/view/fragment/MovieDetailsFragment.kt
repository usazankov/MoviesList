package ru.sample.movies.presentation.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_details_movie.*

import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.presentation.di.components.MoviesComponent
import ru.sample.movies.presentation.view.interfaces.MovieDetailsView
import ru.sample.movies.presentation.view.presenter.MovieDetailsPresenter
import ru.sample.movies.presentation.view.utils.UIParam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.layout_error.*
import ru.sample.movies.R
import ru.sample.movies.presentation.view.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MovieDetailsFragment : BaseFragment(), MovieDetailsView{

    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var movieDetailsPresenter: MovieDetailsPresenter

    @Inject
    lateinit var picasso: Picasso

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
        val view = inflater.inflate(R.layout.fragment_details_movie, container, false)
        initToolBar(view, inflater)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        initialize()
    }

    private fun setupViews(){
        bvp.setHideControlsOnPlay(true)
        swipe_refresh_det.setOnRefreshListener {
            movieDetailsPresenter.initialize(movieId(), true)
        }
    }

    private fun setupTrailer(path: String) {
        bvp.reset()
        bvp.setSource(Uri.parse(path))
    }

    override fun onPause() {
        super.onPause()
        if(bvp != null) bvp.pause()
    }

    private fun initialize() {
        movieDetailsPresenter.initialize(movieId(),false)
    }

    override fun renderMovie(movie: Movie) {

        //Title
        tv_detail_title.text = movie.title
        val genres = movie.genres.joinToString{it.name}
        tv_genre.text = genres
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var dateString = ""
        val date = movie.release_date
        if( date != null){
            dateString = format.format(date)
        }
        tv_release_year.text = dateString
        picasso.load(movie.poster_path)
            .error(R.drawable.photo)
            .into(iv_backdrop)

        tv_overview.text = movie.overview
        tv_vote_average.text = movie.vote_average?.toString()
        tv_vote_count.text = movie.vote_count?.toString()
        tv_original_title.text = movie.original_title
        tv_release_date.text = dateString
        tv_popul.text = movie.popularity?.toString()
        tv_adult.text = if(movie.adult) "Yes" else "No"
        tv_language.text = Utils.mapELanguage(movie.original_language)
        setupTrailer(movie.video)
    }

    override fun showLoading() {
        swipe_refresh_det.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh_det.isRefreshing = false
    }

    override fun hideError() {
        rl_error?.visibility = View.GONE
    }

    override fun showError(message: String) {
        rl_error?.visibility = View.VISIBLE
    }

    fun context(): Context {
        return this.activity!!
    }

    private fun movieId():Int {
        return arguments?.getInt(UIParam.STATE_PARAM_BANK_ID) ?: -1
    }

}