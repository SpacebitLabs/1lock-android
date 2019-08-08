package com.spacebitlabs.onelock.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spacebitlabs.onelock.data.DataStore
import com.spacebitlabs.onelock.data.LoginData

class ListViewModel(val dataStore: DataStore): ViewModel() {

    val loginListState: MutableLiveData<LoginListViewState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
    }

    fun getAllLogins(): List<LoginData> {
        return dataStore.database.getAllLogins()
    }

    sealed class LoginListViewState {

    }
}