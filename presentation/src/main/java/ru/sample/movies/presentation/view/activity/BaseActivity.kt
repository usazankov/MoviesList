package ru.sample.movies.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.sample.movies.R
import ru.sample.movies.presentation.AndroidApplication
import ru.sample.movies.presentation.di.components.ApplicationComponent
import ru.sample.movies.presentation.view.fragment.BaseFragment
import ru.sample.presentation.internal.di.modules.ActivityModule
import ru.sample.presentation.navigation.Navigator
import ru.sample.presentation.view.activity.listeners.OnBackPressed
import javax.inject.Inject

/**
 * Base [android.app.Activity] class for every Activity in this application.
 */
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return [ApplicationComponent]
     */
    protected val applicationComponent: ApplicationComponent
        get() = (getApplication() as AndroidApplication).applicationComponent

    /**
     * Get an Activity module for dependency injection.
     *
     * @return [ActivityModule]
     */
    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent.inject(this)
    }

    /**
     * Adds a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun addFragment(containerViewId: Int, fragment: BaseFragment) {
        val fragmentTransaction = this.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    /**
     * Adds a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun addFragment(containerViewId: Int, fragment: BaseFragment, tag: String) {
        val fragmentTransaction = this.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    /**
     * Replace a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = this.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * Replace a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = this.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment, tag)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)
        if (fragment !is OnBackPressed || !(fragment as OnBackPressed).onBackPressed()) {
            super.onBackPressed()
        }
    }
}