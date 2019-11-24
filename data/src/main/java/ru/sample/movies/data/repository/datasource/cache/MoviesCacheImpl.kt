package ru.sample.movies.data.repository.datasource.cache

import android.content.Context
import io.reactivex.Observable
import ru.sample.movies.data.repository.datasource.serializer.Serializer
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage
import ru.sample.movies.domain.executor.ThreadExecutor
import java.io.File
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(context: Context, serializer: Serializer, fileManager: FileManager, threadExecutor: ThreadExecutor) :
    BaseCache(context, serializer, fileManager, threadExecutor,
        SETTINGS_KEY_LAST_CACHE_UPDATE
    ), IMoviesCache {

    init {
        val rootDir = context.cacheDir
        val path = File(rootDir, "movies/")
        if (!path.exists()) {
            path.mkdir()
        }
        cacheDir = path
    }

    companion object{
        const val SETTINGS_KEY_LAST_CACHE_UPDATE = "movie_cache_update"
        const val MOVIE_FILE_NAME = "movie_"
        const val MOVIES_PAGE_NAME = "movie_page_"
    }

    override fun getMovie(movieId: Int?): Observable<Movie> {
        return Observable.create {
            if(movieId == null) it.onError(RuntimeException("movie id is null"))
            val movieEntityFile = buildFileMovie(movieId)
            val fileContent = fileManager.readFileContent(movieEntityFile)
            val movieEntity: Movie = serializer.deserialize(fileContent, Movie::class.java)
            if (movieEntity.id != null) {
                it.onNext(movieEntity)
                it.onComplete()
            } else {
                it.onError(RuntimeException("movie id not found"))
            }
        }
    }

    override fun getMoviesPage(page: Int?): Observable<MoviesPage> =
        Observable.create {
            val moviesEntityFile = buildFilePageMovies(page)
            val fileContent = fileManager.readFileContent(moviesEntityFile)
            val moviesPage: MoviesPage = serializer.deserialize(fileContent, MoviesPage::class.java)
            if (validateMoviesPage(moviesPage)) {
                it.onNext(moviesPage)
                it.onComplete()
            } else {
                it.onError(RuntimeException("movie id not found"))
            }
        }

    private fun validateMoviesPage(moviesPage: MoviesPage): Boolean {
        for (item in moviesPage.results) {
            if (item.id == null) return false
        }
        return true
    }

    override fun putMoviesPage(moviesPage: MoviesPage) {
        val movieEntityFile = this.buildFilePageMovies(moviesPage.page)
        if (!isCachedMoviesPage(moviesPage.page)) {
            val jsonString = this.serializer.serialize(moviesPage, MoviesPage::class.java)
            this.executeAsynchronously(
                CacheWriter(
                    this.fileManager,
                    movieEntityFile,
                    jsonString
                )
            )
            setLastCacheUpdateTimeMillis()
        }
    }

    override fun putMovie(movie: Movie) {
        if(movie.id != null){
            val movieFile = this.buildFileMovie(movie.id)
            if (!isCachedMovie(movie.id)) {
                val jsonString = this.serializer.serialize(movie, Movie::class.java)
                this.executeAsynchronously(
                    CacheWriter(
                        this.fileManager,
                        movieFile,
                        jsonString
                    )
                )
                setLastCacheUpdateTimeMillis()
            }
        }
    }

    override fun isCachedMovie(movieId: Int?): Boolean {
        if(movieId == null) return false
        val movieEntityFile = this.buildFileMovie(movieId)
        return this.fileManager.exists(movieEntityFile)
    }

    override fun isCachedMoviesPage(page: Int?): Boolean {
        val movieEntityFile = this.buildFilePageMovies(page)
        return this.fileManager.exists(movieEntityFile)
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @return A valid file.
     */
    private fun buildFilePageMovies(page: Int?): File {
        val fileNameBuilder = StringBuilder()
        fileNameBuilder.append(this.cacheDir.path)
        fileNameBuilder.append(File.separator)
        fileNameBuilder.append(MOVIES_PAGE_NAME)
        fileNameBuilder.append(page)
        return File(fileNameBuilder.toString())
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param movieId The id movie to build the file.
     * @return A valid file.
     */
    private fun buildFileMovie(movieId: Int?): File {
        val fileNameBuilder = StringBuilder()
        fileNameBuilder.append(this.cacheDir.path)
        fileNameBuilder.append(File.separator)
        fileNameBuilder.append(MOVIE_FILE_NAME)
        if(movieId != null) fileNameBuilder.append(movieId)
        return File(fileNameBuilder.toString())
    }

}