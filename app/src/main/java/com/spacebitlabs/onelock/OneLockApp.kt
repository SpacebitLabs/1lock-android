package com.spacebitlabs.onelock

import android.app.Application
import timber.log.Timber

/**
 * Created by afzal on 2017-09-04.
 */
class OneLockApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Injection.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}