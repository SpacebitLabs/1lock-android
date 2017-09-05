package com.spacebitlabs.onelock

import com.spacebitlabs.onelock.list.Password

/**
 * Created by afzal on 2017-09-04.
 */
class MockData {
    companion object {

        var PASSWORDS : List<Password>

        init {
            val password1 = Password("Google", "abc@xyz.com", "helloworld")
            val password2 = Password("Reddit", "abc@xyz.com", "helloworld")
            val password3 = Password("Twitter", "abc@xyz.com", "helloworld")
            val password4 = Password("Gmail", "abc@xyz.com", "helloworld")
            val password5 = Password("Facebook", "abc@xyz.com", "helloworld")
            val password6 = Password("Yahoo", "abc@xyz.com", "helloworld")
            val password7 = Password("Snapchat", "abc@xyz.com", "helloworld")
            val password8 = Password("Instagram", "abc@xyz.com", "helloworld")
            val password9 = Password("LinkedIn", "abc@xyz.com", "helloworld")

            PASSWORDS = listOf(password1, password2, password3, password4, password5, password6, password7, password8, password9)
        }
    }
}

