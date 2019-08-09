package com.spacebitlabs.onelock.crypto

import java.security.SecureRandom
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Cryptor {

    fun encrypt(password: String, stringToEncrypt: String): String {
        val encrypted = encrypt(password, stringToEncrypt.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(password: String, stringToDecrypt: String): String? {
        val encrypted = Base64.getDecoder().decode(stringToDecrypt)
        try {
            val decrypted = decrypt(password, encrypted)
            return String(decrypted)
        } catch (ex: BadPaddingException) {
            return null
        }
    }

    fun encrypt(password: String, dataToEncrypt: ByteArray): ByteArray {
        val salt = ByteArray(SALT_SIZE)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(salt)

        val secretKey = generateKey(password, salt).encoded
        val key = Arrays.copyOfRange(secretKey, 0, 32)
        val iv = Arrays.copyOfRange(secretKey, 240, secretKey.size)
        val secretKeySpec = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance(CIPHER_AES_CBC_PADDING)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

        val encrypted = cipher.doFinal(dataToEncrypt)
        val encryptedSalted = ByteArray(encrypted.size + salt.size)
        System.arraycopy(salt, 0, encryptedSalted, 0, salt.size)
        System.arraycopy(encrypted, 0, encryptedSalted, salt.size, encrypted.size)

        return encryptedSalted
    }

    @Throws(BadPaddingException::class)
    fun decrypt(password: String, dataToDecrypt: ByteArray): ByteArray {
        val salt = Arrays.copyOfRange(dataToDecrypt, 0, SALT_SIZE)
        val encrypted = Arrays.copyOfRange(dataToDecrypt, SALT_SIZE, dataToDecrypt.size)

        val secretKey = generateKey(password, salt).encoded
        val key = Arrays.copyOfRange(secretKey, 0, 32)
        val iv = Arrays.copyOfRange(secretKey, 240, secretKey.size)
        val secretKeySpec = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance(CIPHER_AES_CBC_PADDING)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

        return cipher.doFinal(encrypted)
    }

    private fun generateKey(password: String, salt: ByteArray): SecretKey {
        val iterationCount = ITERATION_COUNT
        val keyLength = KEY_LENGTH

        val keySpec = PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength * 8)
        val secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        return secretKeyFactory.generateSecret(keySpec)
    }

    companion object {
        private const val SALT_SIZE = 128
        private const val ITERATION_COUNT = 12000
        private const val KEY_LENGTH = 256
        private const val ALGORITHM = "PBKDF2WithHmacSHA1"
        private const val CIPHER_AES_CBC_PADDING = "AES/CBC/PKCS5Padding"
    }
}