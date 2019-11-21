package ru.sample.movies.presentation.view.interfaces

import androidx.paging.PagedList
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage

interface SelectMoviesDataView : LoadDataView, MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun renderMoviesList(pagedList: PagedList<Movie>)

    @StateStrategyType(SkipStrategy::class)
    fun viewMoviesDetails(movie: Movie)

}