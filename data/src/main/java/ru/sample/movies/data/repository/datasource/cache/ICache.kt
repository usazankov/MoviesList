package ru.sample.movies.data.repository.datasource.cache

interface ICache {
    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    fun isExpired(): Boolean

    /**
     * Evict all elements of the cache.
     */
    fun evictAll()
}