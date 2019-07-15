package com.spacebitlabs.onelock

import com.spacebitlabs.onelock.data.Password

/**
 * Created by afzal on 2017-09-04.
 */
class MockData {
    companion object {

        val PASSWORDS: List<Password> = listOf(
            Password("Google", "abc@xyz.com", "helloworld"),
            Password("Reddit", "abc@xyz.com", "helloworld"),
            Password("Twitter", "abc@xyz.com", "helloworld"),
            Password("Gmail", "abc@xyz.com", "helloworld"),
            Password("Facebook", "abc@xyz.com", "helloworld"),
            Password("Yahoo", "abc@xyz.com", "helloworld"),
            Password("Snapchat", "abc@xyz.com", "helloworld"),
            Password("Instagram", "abc@xyz.com", "helloworld"),
            Password("LinkedIn", "abc@xyz.com", "helloworld")
        )

    }
}

