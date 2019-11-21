package ru.sample.movies.presentation.view.activity

import android.os.Bundle
import ru.sample.movies.R
import ru.sample.movies.presentation.view.fragment.SelectMoviesFragment
import ru.sample.presentation.internal.di.HasComponent
import ru.sample.presentation.internal.di.components.DaggerMoviesComponent
import ru.sample.presentation.internal.di.components.MoviesComponent

/**
 * Main application screen. This is the app entry point.
 */
class MainActivity: BaseActivity(), HasComponent<MoviesComponent> {

    override lateinit var component: MoviesComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        initializeInjector()
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, SelectMoviesFragment())
        }
    }

    private fun initializeInjector() {
        this.component = DaggerMoviesComponent.builder()
            .applicationComponent(applicationComponent)
            .activityModule(activityModule)
            .build()
        applicationComponent.inject(this)
    }
}