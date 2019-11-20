package ru.sample.movies.data.net

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sample.movies.domain.entity.MoviesPage
import java.util.concurrent.TimeUnit

class MoviesApiTest {

    lateinit var retrofit: Retrofit;
    lateinit var mockWebServer: MockWebServer;

    val get = "GET"

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @After
    fun close(){
        mockWebServer.shutdown()
    }

    @Test
    fun setviceDescrTest(){
        mockWebServer.enqueue(MockResponse().setBody(test_movies_page))
        val service = retrofit.create(MoviesApi::class.java)
        val call = service.moviesList()
        var moviesPage: MoviesPage? = null
        call.subscribe{
            moviesPage = it
        }

        val req = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        Assert.assertEquals(req.method, get)
        Assert.assertEquals(req.path, "/LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/movies.json")
        Assert.assertNotNull(moviesPage)
        Assert.assertEquals(moviesPage?.page, 1)
        Assert.assertEquals(moviesPage?.results?.size, 4)
        Assert.assertEquals(moviesPage?.total_pages, 1)
        Assert.assertEquals(moviesPage?.total_results, 3)
    }
}