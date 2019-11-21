package ru.sample.movies.presentation.view.interfaces

import com.arellomobile.mvp.MvpView
import ru.sample.movies.domain.entity.Movie

interface MovieDetailsView : LoadDataView, MvpView {
    fun renderMovie(movie: Movie)
}