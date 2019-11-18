package ru.sample.movies.data.repository.datasource.cache

import android.content.Context
import ru.sample.movies.data.repository.datasource.serializer.Serializer
import ru.sample.movies.domain.executor.ThreadExecutor
import java.io.File

abstract class BaseCache(
    protected val context: Context,
    protected val serializer: Serializer,
    protected val fileManager: FileManager,
    protected val threadExecutor: ThreadExecutor,
    private val settingsKey: String
) : ICache {
    private var expirationTime: Long = 0

    protected var cacheDir: File

    companion object {
        protected val SETTINGS_FILE_NAME = "ru.inpas.enrollapp.SETTINGS"
        private val DEFAULT_EXPIRATION_TIME = (120 * 1000).toLong()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.lastCacheUpdateTimeMillis

        if (expirationTime == 0L) return false

        val expired = currentTime - lastUpdateTime > expirationTime

        if (expired) {
            this.evictAll()
        }

        return expired
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    protected val lastCacheUpdateTimeMillis: Long
        get() = this.fileManager.getFromPreferences(
            context,
            SETTINGS_FILE_NAME,
            settingsKey
        )

    init {
        this.cacheDir = context.cacheDir
        expirationTime =
            DEFAULT_EXPIRATION_TIME
    }

    fun setExpirationTime(time: Long) {
        expirationTime = time
    }

    override fun evictAll() {
        executeAsynchronously(
            CacheEvictor(
                fileManager,
                cacheDir
            )
        )
    }


    /**
     * Set in millis, the last time the cache was accessed.
     */
    protected fun setLastCacheUpdateTimeMillis() {
        val currentMillis = System.currentTimeMillis()
        this.fileManager.writeToPreferences(
            this.context,
            SETTINGS_FILE_NAME,
            settingsKey, currentMillis
        )
    }

    /**
     * Executes a [Runnable] in another Thread.
     *
     * @param runnable [Runnable] to execute
     */
    protected fun executeAsynchronously(runnable: Runnable) {
        this.threadExecutor.execute(runnable)
    }

    protected fun write(fileToWrite: File, fileContent: String) {
        this.fileManager.writeToFile(fileToWrite, fileContent)
    }

    /**
     * [Runnable] class for writing to disk.
     */
    class CacheWriter(
        private val fileManager: FileManager,
        private val fileToWrite: File,
        private val fileContent: String
    ) : Runnable {

        override fun run() {
            this.fileManager.writeToFile(fileToWrite, fileContent)
        }
    }

    protected fun clear() {
        this.fileManager.clearDirectory(this.cacheDir)
    }

    /**
     * [Runnable] class for evicting all the cached files
     */
    class CacheEvictor(private val fileManager: FileManager, private val cacheDir: File) :
        Runnable {

        override fun run() {
            this.fileManager.clearDirectory(this.cacheDir)
        }
    }
}