package ru.sample.movies.data.repository

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Matchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.sample.movies.data.repository.datasource.IMovieDataStore
import ru.sample.movies.data.repository.datasource.MoviesDataStoreFactory
import ru.sample.movies.domain.entity.Movie
import ru.sample.movies.domain.entity.MoviesPage

class MoviesRepositoryTest {

    private val FAKE_MOVIE_ID = 5
    private val FAKE_PAGE_ID = 1

    private var moviesRepository: MoviesRepository? = null

    @Mock
    lateinit var mockMoviesDataStoreFactory: MoviesDataStoreFactory

    @Mock
    lateinit var mockMoviesDataStore: IMovieDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesRepository = MoviesRepository(mockMoviesDataStoreFactory)
        given(mockMoviesDataStoreFactory.createWithCacheMoviesPage(FAKE_PAGE_ID)).willReturn(mockMoviesDataStore)
        given(mockMoviesDataStoreFactory.createWithCacheDetailsMovie(anyInt())).willReturn(mockMoviesDataStore)
    }

    @Test
    fun testGetListBanks() {

        val moviesPage = MoviesPage()

        given(mockMoviesDataStore.listMovies(FAKE_PAGE_ID)).willReturn(
            Observable.just(
                moviesPage
            )
        )

        moviesRepository!!.listMovies(FAKE_PAGE_ID, false)

        verify(mockMoviesDataStoreFactory).createWithCacheMoviesPage(FAKE_PAGE_ID)
        verify(mockMoviesDataStore).listMovies(FAKE_PAGE_ID)
    }

    @Test
    fun testGetBankDescription() {
        val movie = mock(Movie::class.java)
        given(mockMoviesDataStore.moviesDescription(FAKE_MOVIE_ID)).willReturn(
            Observable.just(
                movie
            )
        )

        moviesRepository!!.movieDescription(FAKE_MOVIE_ID, false)
        verify(mockMoviesDataStoreFactory).createWithCacheDetailsMovie(
            FAKE_MOVIE_ID
        )
        verify(mockMoviesDataStore).moviesDescription(FAKE_MOVIE_ID)
    }
}