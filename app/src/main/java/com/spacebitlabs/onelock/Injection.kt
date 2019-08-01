package com.spacebitlabs.onelock

import android.annotation.SuppressLint
import android.content.Context
import com.spacebitlabs.onelock.data.DataStore
import com.spacebitlabs.onelock.data.MyObjectBox
import com.spacebitlabs.onelock.data.OneLockDatabase
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber

class Injection private constructor(private val appContext: Context) {

    private val boxStore: BoxStore by lazy {
        val store = MyObjectBox.builder().androidContext(provideContext()).build()

        if (BuildConfig.DEBUG) {
            Timber.d("Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
            AndroidObjectBrowser(store).start(provideContext())
        }

        return@lazy store
    }

    private val oneLockDatabase: OneLockDatabase by lazy {
        OneLockDatabase(provideBoxStore())
    }

    private val dataStore: DataStore by lazy {
        DataStore(provideOneLockDatabase())
    }

    fun provideContext(): Context = appContext

    fun provideBoxStore(): BoxStore = boxStore

    fun provideOneLockDatabase(): OneLockDatabase = oneLockDatabase

    fun provideDataStore(): DataStore = dataStore

    companion object {
        const val DATABASE_FILE_NAME: String = "onelock_data"

        @SuppressLint("StaticFieldLeak", "Keeping app context is safe here")
        @Volatile
        private lateinit var INSTANCE: Injection

        fun init(appContext: Context) {
            INSTANCE = Injection(appContext)
        }

        fun get(): Injection {
            return INSTANCE
        }
    }
}