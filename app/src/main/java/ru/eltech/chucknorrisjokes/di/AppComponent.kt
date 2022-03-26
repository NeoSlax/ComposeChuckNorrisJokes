package ru.eltech.chucknorrisjokes.di

import dagger.Component
import ru.eltech.chucknorrisjokes.presetation.MainActivity

@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        DomainModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
}