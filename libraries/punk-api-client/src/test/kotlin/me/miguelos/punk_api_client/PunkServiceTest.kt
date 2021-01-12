package me.miguelos.punk_api_client

import junit.framework.TestCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

internal class PunkServiceTest : TestCase() {

    private var retrofit: Retrofit? = null
    private var punkService: PunkService? = null


    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/")
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        punkService = retrofit?.create(PunkService::class.java)
    }

    @Throws(Exception::class)
    fun testBeersRetrieval() {
        val response = punkService?.getBeers()
            ?.blockingGet()
        response?.run {
            assertTrue(isNotEmpty())
        }
    }
}
