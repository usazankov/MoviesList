package ru.sample.movies.presentation.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import java.security.KeyStore
import java.security.cert.CertificateFactory

@Module
class KeyStoreModule {

    @Provides
    fun keyStore(context: Context): KeyStore{
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val keyStore = KeyStore.getInstance("PKCS12")
        val inputCa1 = context.assets.open("cert.cer")
        val inputCa2 = context.assets.open("cert2.cer")
        keyStore.load(null, null)
        keyStore.setCertificateEntry("github.com", certificateFactory.generateCertificate(inputCa1))
        keyStore.setCertificateEntry("wikimedia.org", certificateFactory.generateCertificate(inputCa2))
        return keyStore;
    }
}