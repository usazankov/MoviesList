package ru.sample.movies.presentation.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.sample.movies.R
import ru.sample.presentation.internal.di.HasComponent
import ru.sample.presentation.view.fragment.moxy.MvpAndroidxFragment

/**
 * Base {@link androidx.fragment.app.Fragment} class for every fragment in this application.
 */
abstract class BaseFragment : MvpAndroidxFragment() {

    private lateinit var toolbar: Toolbar

    private var title_action_bar: TextView? = null

    protected fun initToolBar(v: View, layoutInflater: LayoutInflater) {
        toolbar = v.findViewById(R.id.toolbar)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        val actionBar = appCompatActivity.getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowCustomEnabled(true)
            val v = layoutInflater.inflate(R.layout.action_bar, null)
            title_action_bar = v.findViewById(R.id.title_action_bar)
            actionBar.setCustomView(v)
        }
        toolbar.setNavigationOnClickListener(toolbarBackButtonListener)
    }

    private val toolbarBackButtonListener = View.OnClickListener { activity?.onBackPressed() }

    protected fun setTitleToolBar(resId: Int) {
        title_action_bar?.setText(resId)
    }

    @SuppressWarnings("unchecked")
    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast((activity as HasComponent<C>).component)
    }
}