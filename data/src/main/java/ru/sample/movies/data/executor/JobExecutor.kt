package ru.sample.movies.data.executor

import ru.sample.movies.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor @Inject constructor() : ThreadExecutor {
    private val threadExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        3,
        5,
        10,
        TimeUnit.SECONDS,
        LinkedBlockingDeque(),
        JobThreadFactory()
    )

    override fun execute(p0: Runnable) {
        threadExecutor.execute(p0)
    }

    class JobThreadFactory : ThreadFactory{
        private var counter = 0
        override fun newThread(p0: Runnable) = Thread(p0, "android_" + counter)
    }

}