package com.spacebitlabs.onelock

import com.spacebitlabs.onelock.data.LoginData

/**
 * Created by afzal on 2017-09-04.
 */
class MockData {
    companion object {

        val LOGINS: List<LoginData> = listOf(
            LoginData("Google", "abc@xyz.com", "helloworld"),
            LoginData("Reddit", "abc@xyz.com", "helloworld"),
            LoginData("Twitter", "abc@xyz.com", "helloworld"),
            LoginData("Gmail", "abc@xyz.com", "helloworld"),
            LoginData("Facebook", "abc@xyz.com", "helloworld"),
            LoginData("Yahoo", "abc@xyz.com", "helloworld"),
            LoginData("Snapchat", "abc@xyz.com", "helloworld"),
            LoginData("Instagram", "abc@xyz.com", "helloworld"),
            LoginData("LinkedIn", "abc@xyz.com", "helloworld")
        )

    }
}

