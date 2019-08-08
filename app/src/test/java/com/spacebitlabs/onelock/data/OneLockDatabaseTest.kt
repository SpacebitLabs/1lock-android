package com.spacebitlabs.onelock.data

import com.google.common.truth.Truth.assertThat
import io.objectbox.BoxStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class OneLockDatabaseTest {

    private lateinit var oneLockDb: OneLockDatabase
    private lateinit var store: BoxStore

    @Before
    fun setUp() {
        // store the database in the systems temporary files folder
        val tempFile = File.createTempFile("object-store-test", "")
        // ensure file does not exist so builder creates a directory instead
        tempFile.delete()
        store = MyObjectBox.builder().directory(tempFile).build()
        oneLockDb = OneLockDatabase(store)
    }

    @After
    fun tearDown() {
        store.close()
        store.deleteAllFiles()
    }

    @Test
    fun addLogin() {
        val expected = LoginData(
            name = "Test",
            username = "TestUsername",
            password = "TestPassword"
        )
        oneLockDb.addLogin(expected)

        val login = oneLockDb.getAllLogins()[0]

        assertThat(login).isEqualTo(expected)
    }

    @Test
    fun removeLogin() {
        val loginData = LoginData(
            name = "Test",
            username = "TestUsername",
            password = "TestPassword"
        )

        oneLockDb.addLogin(loginData)

        assertThat(oneLockDb.getAllLogins().size).isGreaterThan(0)
        val login = oneLockDb.getAllLogins()[0]

        oneLockDb.deleteLogin(login.id)
        assertThat(oneLockDb.getAllLogins().size).isEqualTo(0)
    }
}