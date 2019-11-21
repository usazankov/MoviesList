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
     * Show a retry view in case of an error when retrieving data.
     */
    @StateStrategyType(SkipStrategy::class)
    fun showRetry(message: String)

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @StateStrategyType(SkipStrategy::class)
    fun hideRetry()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)

    /**
     * Hide swipe refresh
     *
     */
    @StateStrategyType(SkipStrategy::class)
    fun hideRefresh()
}