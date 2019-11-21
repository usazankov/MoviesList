package ru.sample.presentation.view.fragment.moxy

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpDelegate

open class MvpAndroidxFragment : Fragment() {

    private var mIsStateSaved: Boolean = false
    private var mMvpDelegate: MvpDelegate<MvpAndroidxFragment> = MvpDelegate(this)

    /**
     * @return The [MvpDelegate] being used by this Fragment.
     */
    val mvpDelegate: MvpDelegate<MvpAndroidxFragment>
        get() {
            return mMvpDelegate
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        mIsStateSaved = false

        mvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()

        mIsStateSaved = false

        mvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mIsStateSaved = true

        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()

        mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mvpDelegate.onDetach()
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mIsStateSaved) {
            mIsStateSaved = false
            return
        }

        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving || activity!!.isFinishing()) {
            mvpDelegate.onDestroy()
        }
    }
}