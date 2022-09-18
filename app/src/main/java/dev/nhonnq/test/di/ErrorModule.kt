package dev.nhonnq.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nhonnq.test.errors.ErrorManager
import dev.nhonnq.test.errors.ErrorUseCase
import dev.nhonnq.test.errors.mapper.ErrorMapper
import dev.nhonnq.test.errors.mapper.ErrorMapperSource
import javax.inject.Singleton

// With @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
