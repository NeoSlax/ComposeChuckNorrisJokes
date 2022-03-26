package ru.eltech.chucknorrisjokes.di

import dagger.Module
import dagger.Provides
import ru.eltech.chucknorrisjokes.data.network.ApiFactory
import ru.eltech.chucknorrisjokes.data.network.ApiService

@Module
object ApiModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}