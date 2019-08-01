package com.spacebitlabs.onelock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spacebitlabs.onelock.data.DataStore

/**
 * Constructs a ViewModelFactory with dependencies
 */
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val parameterTypes = modelClass.constructors[0].parameterTypes
        val parameters = mutableListOf<Any>()

        val injection = Injection.get()

        for (type in parameterTypes) {
            val instanceOfType = when(type) {
                DataStore::class.java -> injection.provideDataStore()
                else -> throw NoSuchElementException("Injection does not have this type, or not declared in ViewModelFactory")
            }

            parameters.add(instanceOfType)
        }

        @Suppress("UNCHECKED_CAST")
        return modelClass.constructors[0].newInstance(*parameters.toTypedArray()) as T
    }
}