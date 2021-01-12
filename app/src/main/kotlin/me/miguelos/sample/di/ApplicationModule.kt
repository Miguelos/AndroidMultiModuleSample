package me.miguelos.sample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.miguelos.base.interactors.ExecutionSchedulers
import me.miguelos.base.interactors.AppSchedulers
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideContext(): Context = Application()

    @Provides
    fun provideExecutionSchedulers(): ExecutionSchedulers = AppSchedulers()

    @Provides
    fun provideLogger(): Timber.Tree = Timber.DebugTree()
}
