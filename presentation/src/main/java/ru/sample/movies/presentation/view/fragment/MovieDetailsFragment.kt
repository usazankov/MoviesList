package ru.sample.movies.presentation.view.fragment

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.halilibo.bvpkotlin.captions.CaptionsView
import kotlinx.android.synthetic.main.fragment_details_movie.*

import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.presentation.di.components.MoviesComponent
import ru.sample.movies.presentation.view.interfaces.MovieDetailsView
import ru.sample.movies.presentation.view.presenter.MovieDetailsPresenter
import ru.sample.movies.presentation.view.utils.UIParam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.action_bar.view.*
import kotlinx.android.synthetic.main.content_detail.*
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
        val fragmentView = inflater.inflate(R.layout.fragment_details_movie, container, false)
        initToolBar(fragmentView, inflater)
        initProgressBar(fragmentView)
        //setTitleToolBar(R.string.title_list_banks)

        return fragmentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupVideo()
    }

    private fun setupVideo(){
        bvp.setHideControlsOnPlay(true)
    }

    private fun setupTrailer(path: String) {
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
        tv_detail_title.setText(movie.title)
        val genres = movie.genres.joinToString{it.name}
        tv_genre.setText(genres)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var dateString = ""
        if(movie.release_date != null){
            dateString = format.format(movie.release_date)
        }

        tv_release_year.setText(dateString)
        picasso.load(movie.poster_path)
            .error(R.drawable.photo)
            .into(iv_backdrop)

        tv_overview.setText(movie.overview)
        tv_vote_average.setText(movie.vote_average?.toString())
        tv_vote_count.setText(movie.vote_count?.toString())
        tv_original_title.setText(movie.original_title)
        tv_release_date.setText(dateString)
        tv_popul.setText(movie.popularity?.toString())
        tv_adult.setText(if(movie.adult) "Yes" else "No")
        tv_language.setText(Utils.mapELanguage(movie.original_language))
        setupTrailer(movie.video)
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
        movieDetailsPresenter.initialize(movieId(),false)
    }

    fun context(): Context {
        return this.activity!!
    }

    fun movieId():Int {
        val id = arguments?.getInt(UIParam.STATE_PARAM_BANK_ID) ?: -1
        return id
    }

}