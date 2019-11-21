package ru.sample.movies.presentation.view.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.sample.movies.presentation.AndroidApplication

open class BasePresenter<T: MvpView> : MvpPresenter<T>() {

    val component = AndroidApplication.application.applicationComponent

}