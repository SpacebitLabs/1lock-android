package com.spacebitlabs.onelock.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class LoginData(
    val name: String,
    val username: String,
    val password: String,
    @Id
    var id: Long = 0
)