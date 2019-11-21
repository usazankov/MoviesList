package ru.sample.presentation.internal.di.modules

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Module(includes = arrayOf(KeyStoreModule::class, NetModule::class))
class PicassoModule {

    @Provides
    fun picasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso =
        Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build()

    @Provides
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader = OkHttp3Downloader(okHttpClient)

}