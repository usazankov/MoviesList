package ru.sample.presentation.internal.di.components

import dagger.Component
import ru.sample.presentation.internal.di.PerActivity
import ru.sample.presentation.internal.di.modules.ActivityModule

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
}