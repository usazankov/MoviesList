package ru.sample.presentation.internal.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import ru.sample.presentation.internal.di.PerActivity

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
class ActivityModule(private val activity: Activity) {

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun activity(): Activity {
        return this.activity
    }

//    @Provides
//    @PerActivity
//    internal fun provideDialogManager(): DialogManager {
//        return DialogManager(activity)
//    }

}