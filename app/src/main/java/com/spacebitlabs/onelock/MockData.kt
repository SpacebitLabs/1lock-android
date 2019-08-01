package com.spacebitlabs.onelock

import com.spacebitlabs.onelock.data.Password

/**
 * Created by afzal on 2017-09-04.
 */
class MockData {
    companion object {

        val PASSWORDS: List<Password> = listOf(
            Password(0,"Google", "abc@xyz.com", "helloworld"),
            Password(1,"Reddit", "abc@xyz.com", "helloworld"),
            Password(2,"Twitter", "abc@xyz.com", "helloworld"),
            Password(3,"Gmail", "abc@xyz.com", "helloworld"),
            Password(4,"Facebook", "abc@xyz.com", "helloworld"),
            Password(5,"Yahoo", "abc@xyz.com", "helloworld"),
            Password(6,"Snapchat", "abc@xyz.com", "helloworld"),
            Password(7,"Instagram", "abc@xyz.com", "helloworld"),
            Password(8,"LinkedIn", "abc@xyz.com", "helloworld")
        )

    }
}

