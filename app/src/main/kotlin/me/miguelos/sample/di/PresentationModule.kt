package me.miguelos.sample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.miguelos.base.mappers.TwoWaysMapper
import me.miguelos.sample.presentation.model.Beer
import me.miguelos.sample.presentation.model.mappers.BeerMapper
import me.miguelos.base.util.imageloader.GlideImageLoader
import me.miguelos.base.util.imageloader.ImageLoader
import javax.inject.Singleton
import me.miguelos.sample.domain.model.Beer as DomainBeer


@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideBeerMapper(): TwoWaysMapper<DomainBeer, Beer> =
        BeerMapper()

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
        GlideImageLoader(context)
}
