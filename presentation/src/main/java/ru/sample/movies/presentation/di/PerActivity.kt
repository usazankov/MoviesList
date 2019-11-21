package ru.sample.presentation.internal.di

import java.lang.annotation.Retention
import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 */
@Scope
annotation class PerActivity