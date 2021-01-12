# Punk API client

Simple lightweight android library for accessing the [Punk API](https://punkapi.com/documentation/v2).

# Usage

Example usage using Hilt dependency injection.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object PunkApiClientModule {

    @Provides
    @Singleton
    internal fun providePunkService(
        retrofit: Retrofit
    ): PunkService = retrofit.create(PunkService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(Timeouts.CONNECT, TimeUnit.SECONDS)
        .readTimeout(Timeouts.READ, TimeUnit.SECONDS)
        .writeTimeout(Timeouts.WRITE, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}
```