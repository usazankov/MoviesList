package ru.sample.movies.data.repository.datasource

import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.sample.movies.data.net.MoviesApi
import ru.sample.movies.data.repository.datasource.cache.IMoviesCache

class MoviesDataStoreFactoryTest {
    private val FAKE_MOVIE_ID = 123

    private var movieDataStoreFactory: MoviesDataStoreFactory? = null

    @Mock
    lateinit var mockMoviesCache: IMoviesCache

    @Mock
    lateinit var moviesApi: MoviesApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieDataStoreFactory = MoviesDataStoreFactory(moviesApi, mockMoviesCache)
    }

    @Test
    fun testCreateDiskDataStore() {
        given(mockMoviesCache.isCachedMovie(FAKE_MOVIE_ID)).willReturn(true)
        given(mockMoviesCache.isExpired()).willReturn(false)

        val MovieDataStore = movieDataStoreFactory!!.createWithCacheDetailsMovie(FAKE_MOVIE_ID)

        assertThat(MovieDataStore, `is`<Any>(notNullValue()))
        assertThat(MovieDataStore, `is`<Any>(instanceOf<Any>(DiskMoviesDataStore::class.java)))

        verify(mockMoviesCache).isCachedMovie(FAKE_MOVIE_ID)
        verify(mockMoviesCache).isExpired()
    }

    @Test
    fun testCreateCloudDataStore() {

        given(mockMoviesCache.isCachedMovie(FAKE_MOVIE_ID)).willReturn(false)
        given(mockMoviesCache.isExpired()).willReturn(true)

        val MovieDataStore = movieDataStoreFactory!!.createWithCacheDetailsMovie(FAKE_MOVIE_ID)

        assertThat(MovieDataStore, `is`<Any>(notNullValue()))
        assertThat(MovieDataStore, `is`<Any>(instanceOf<Any>(CloudMoviesDataStore::class.java)))

        verify(mockMoviesCache).isExpired()
    }
}