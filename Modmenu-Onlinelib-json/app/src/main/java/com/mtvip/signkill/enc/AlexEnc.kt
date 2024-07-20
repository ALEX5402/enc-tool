package com.mtvip.signkill.enc

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AlexEnc {

    fun ispackageinstalled(packageNames : Array<String> , context : Context)
    {

            val packageManager = context.packageManager

            for (packageName in packageNames) {
                try {
                    val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                    if (packageInfo !== null) {
                        Toast.makeText(context, "$packageNames", Toast.LENGTH_LONG).show()
                        System.exit(1)
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }

        }

    }

    @SuppressLint("GetInstance")
    @TargetApi(Build.VERSION_CODES.O)
    fun encryptString(input: String): Pair<String, String> {
        // Generate a secret key
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        val secretKey: SecretKey = keyGenerator.generateKey()

        // Create a Cipher object and initialize it with the secret key
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        // Encrypt the input string
        val encryptedBytes: ByteArray = cipher.doFinal(input.toByteArray())
        val encryptedString: String = Base64.getEncoder().encodeToString(encryptedBytes)

        // Convert the secret key to a string
        val keyString: String = Base64.getEncoder().encodeToString(secretKey.encoded)

        // Return the encrypted string and the key
        return Pair(encryptedString, keyString)
    }

    @SuppressLint("GetInstance")
    @TargetApi(Build.VERSION_CODES.O)
    fun decryptString(encryptedString: String, key: String): String {
        // Decode the key from Base64
        val decodedKey: ByteArray = Base64.getDecoder().decode(key)
        val secretKey: SecretKey = SecretKeySpec(decodedKey, "AES")

        // Create a Cipher object and initialize it with the secret key
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        // Decrypt the encrypted string
        val encryptedBytes: ByteArray = Base64.getDecoder().decode(encryptedString)
        val decryptedBytes: ByteArray = cipher.doFinal(encryptedBytes)
        val decryptedString: String = String(decryptedBytes)

        // Return the decrypted string
        return decryptedString
    }
}
