package ru.sample.movies.presentation.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.sample.movies.R
import ru.sample.presentation.internal.di.HasComponent
import ru.sample.presentation.view.fragment.moxy.MvpAndroidxFragment

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
abstract class BaseFragment : MvpAndroidxFragment() {

    protected var rl_progress: RelativeLayout? = null

    internal abstract fun onClickRetry()

//    @Inject
//    internal var dialogManager: DialogManager? = null

    private lateinit var toolbar: Toolbar

    private var main_title: TextView? = null

    private var sub_title: TextView? = null

    private var title_action_bar: TextView? = null

    private var logo_action_bar: ImageView? = null

//    @Inject
//    internal var picasso: Picasso? = null

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

    protected fun initProgressBar(v: View){
        rl_progress = v.findViewById(R.id.rl_progress)
    }

    protected fun showProgressBarLoading() {
        this.rl_progress?.setVisibility(View.VISIBLE)
    }

    protected fun hideProgressBarLoading() {
        this.rl_progress?.setVisibility(View.GONE)
    }

    private val toolbarBackButtonListener = View.OnClickListener { activity?.onBackPressed() }

    protected fun setTitleToolBar(resId: Int) {
        title_action_bar?.setText(resId)
    }

    protected fun setTitleToolBar(value: String) {
        title_action_bar?.setText(value)
    }

    protected fun setMainTitle(resId: Int) {
        main_title?.setText(resId)
    }

    protected fun setSubTitle(resId: Int) {
        sub_title?.setText(resId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressWarnings("unchecked")
    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast((activity as HasComponent<C>).component)
    }
}