package ru.sample.movies.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.sample.movies.domain.executor.PostExecutionThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject internal constructor() : PostExecutionThread {
    override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()
}