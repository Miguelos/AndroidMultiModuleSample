package me.miguelos.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.miguelos.base.mappers.Mapper
import me.miguelos.punk_api_client.model.BeerEntity
import me.miguelos.sample.data.PunkRepositoryImpl
import me.miguelos.sample.data.source.remote.PunkRemoteDataSource
import me.miguelos.sample.data.source.remote.entity.mapper.BeerMapper
import me.miguelos.sample.domain.PunkRepository
import javax.inject.Singleton
import me.miguelos.sample.domain.model.Beer as DomainBeer


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providePunkRepository(
        punkRemoteDataSource: PunkRemoteDataSource
    ): PunkRepository =
        PunkRepositoryImpl(punkRemoteDataSource)


    @Provides
    @Singleton
    fun provideBeerEntityMapper(): Mapper<BeerEntity, DomainBeer> =
        BeerMapper()
}
