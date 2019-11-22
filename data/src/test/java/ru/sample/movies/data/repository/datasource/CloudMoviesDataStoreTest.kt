package ru.sample.movies.data.repository.datasource

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.sample.movies.data.net.MoviesApi
import ru.sample.movies.data.repository.datasource.cache.IMoviesCache
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage

class CloudMoviesDataStoreTest {
    
    private val FAKE_MOVIE_ID = 421
    private val FAKE_PAGE_ID = 524

    private var cloudMovieDataStore: CloudMoviesDataStore? = null

    @Mock
    lateinit var moviesApi: MoviesApi

    @Mock
    lateinit var mockMovieCache: IMoviesCache

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cloudMovieDataStore = CloudMoviesDataStore(moviesApi, mockMovieCache)
    }

    @Test
    fun testGetMovieListFromApi() {
        val moviesPage = MoviesPage()
        given(moviesApi.moviesList()).willReturn(
            Observable.just(
                moviesPage
            )
        )

        cloudMovieDataStore!!.listMovies(FAKE_PAGE_ID).subscribe()
        verify(moviesApi).moviesList()
        verify(mockMovieCache).putMoviesPage(moviesPage)
    }

}