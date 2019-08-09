package com.spacebitlabs.onelock.crypto

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class CryptorTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `encrypt then decrypt results in the same string`() {
        val encrypted = Cryptor().encrypt("test", "Hello")
        val decrypted = Cryptor().decrypt("test", encrypted)

        assertThat(encrypted).isNotEqualTo(decrypted)
        assertThat(decrypted).isEqualTo("Hello")
    }

    @Test
    fun `decrypt with wrong password returns null`() {
        val encrypted = Cryptor().encrypt("test", "Hello")
        val decrypted = Cryptor().decrypt("test1", encrypted)

        assertThat(encrypted).isNotEqualTo(decrypted)
        assertThat(decrypted).isNull()
    }
}