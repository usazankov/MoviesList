package ru.sample.movies.data.repository.datasource

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.sample.movies.data.repository.datasource.cache.IMoviesCache

class DiskMoviesDataStoreTest {
    private val FAKE_MOVIE_ID = 123
    private val FAKE_PAGE_ID = 5

    private var diskMovieDataStore: DiskMoviesDataStore? = null

    @Mock
    lateinit var mockMovieCache: IMoviesCache

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        diskMovieDataStore = DiskMoviesDataStore(mockMovieCache)
    }

    @Test
    fun testGetMovieDetailesFromCache() {
        diskMovieDataStore!!.moviesDescription(FAKE_MOVIE_ID)
        verify(mockMovieCache).getMovie(FAKE_MOVIE_ID)
    }

    @Test
    fun testGetMovieListFromCache() {
        diskMovieDataStore!!.listMovies(FAKE_PAGE_ID)
        verify(mockMovieCache).getMoviesPage(FAKE_PAGE_ID)
    }
}