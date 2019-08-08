package com.spacebitlabs.onelock.data

import com.spacebitlabs.onelock.MockData
import io.objectbox.Box
import io.objectbox.BoxStore

class OneLockDatabase(val boxStore: BoxStore) {

    private var box: Box<LoginData> = boxStore.boxFor(LoginData::class.java)

    fun addLogin(loginData: LoginData) {
        box.put(loginData)
    }

    fun deleteLogin(id: Long) {
        box.remove(id)
    }

    fun getAllLogins(): List<LoginData> {
        return box.all
    }

    fun getLogin(id: Long): LoginData {
        return box.get(id)
    }

    fun loadMockSeedData() {
        MockData.LOGINS.forEach {
            addLogin(it)
        }
    }
}
