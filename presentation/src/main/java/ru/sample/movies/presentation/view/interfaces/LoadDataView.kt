package ru.sample.movies.presentation.view.interfaces

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Interface representing a View that will use to load data.
 */
interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @StateStrategyType(SkipStrategy::class)
    fun showLoading()

    /**
     * Hide a loading view.
     */
    @StateStrategyType(SkipStrategy::class)
    fun hideLoading()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)

    /**
     * Hide an error message
     *
     */
    @StateStrategyType(SkipStrategy::class)
    fun hideError()

    /**
     * Hide swipe refresh
     *
     */
    @StateStrategyType(SkipStrategy::class)
    fun hideRefresh()
}