package ru.eltech.chucknorrisjokes.di

import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import ru.eltech.chucknorrisjokes.data.repository.RepositoryImpl
import ru.eltech.chucknorrisjokes.domain.JokesRepository

@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): JokesRepository
}