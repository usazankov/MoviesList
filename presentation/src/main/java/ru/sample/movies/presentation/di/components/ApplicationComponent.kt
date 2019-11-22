package ru.sample.movies.presentation.di.components

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Component
import ru.sample.movies.presentation.di.modules.ApplicationModule
import ru.sample.movies.presentation.di.modules.MoviesModule
import ru.sample.movies.presentation.view.activity.BaseActivity
import ru.sample.movies.presentation.view.activity.MainActivity
import ru.sample.movies.presentation.view.presenter.MovieDetailsPresenter
import ru.sample.movies.presentation.view.presenter.SelectMoviesPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, MoviesModule::class))
interface ApplicationComponent {

    //Activity
    fun inject(baseActivity: BaseActivity)
    fun inject(mainActivity: MainActivity)

    //Picasso
    fun picasso(): Picasso

    //Context
    fun context(): Context

    //Presenters
    fun inject(selectMoviesPresenter: SelectMoviesPresenter)
    fun inject(movieDetailsPresenter: MovieDetailsPresenter)

}