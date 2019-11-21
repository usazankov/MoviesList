package ru.sample.presentation.internal.di.components

import dagger.Component
import ru.sample.movies.presentation.view.fragment.SelectMoviesFragment
import ru.sample.presentation.internal.di.PerActivity
import ru.sample.presentation.internal.di.modules.ActivityModule

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface MoviesComponent : ActivityComponent {
    fun inject(selectBankFragment: SelectMoviesFragment)
//    fun inject(bankDetailsFragment: BankDetailsFragment)
}