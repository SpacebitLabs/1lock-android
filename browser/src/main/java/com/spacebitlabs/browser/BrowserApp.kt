package com.spacebitlabs.browser

import android.app.Application
import timber.log.Timber

/**
 * Created by afzal on 2017-09-04.
 */
class BrowserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}