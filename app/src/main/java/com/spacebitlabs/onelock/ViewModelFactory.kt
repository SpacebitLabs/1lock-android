package com.spacebitlabs.onelock

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.spacebitlabs.onelock.data.DataStore

/**
 * Constructs a ViewModelFactory with dependencies
 */
object ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val parameterTypes = modelClass.constructors[0].parameterTypes
        val injection = Injection.get()
        val parameters = Array<Any>(parameterTypes.size, init = {
            val instanceOfType = when (parameterTypes[it]) {
                DataStore::class.java -> injection.provideDataStore()
                else                  -> throw NoSuchElementException("Injection does not have this type, or not declared in ViewModelFactory")
            }

            return@Array instanceOfType
        })

        // TODO see if there's better Kotlin code than this for this factory
        @Suppress("UNCHECKED_CAST")
        return modelClass.constructors[0].newInstance(*parameters) as T
    }

    fun <T : ViewModel> create(fragment: Fragment, viewModelClass: Class<T>): T {
        return ViewModelProviders.of(fragment, this).get(viewModelClass)
    }
}