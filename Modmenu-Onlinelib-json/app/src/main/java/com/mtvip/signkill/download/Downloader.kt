package com.mtvip.signkill.download

import android.os.AsyncTask
import android.util.Log
import com.mtvip.signkill.enc.AlexEnc
import com.mtvip.signkill.service.Responce
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.net.URL


class Downloader(
    responce:Responce,
    private val downloadlin: String,
    private val zippass: String,
    private val zipkey: String,
) : AsyncTask<Void,Void,String>() {
   private val responce = WeakReference(responce)
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg voids: Void?): String? {
        val alexEnc = AlexEnc()
        try {

            val downloadlink = downloadlin.let {
                URL(it)
            }
            val tempDir = System.getProperty("java.io.tmpdir")
            val timestamp = System.currentTimeMillis()
            val outputfile = File(tempDir, "$timestamp.zip")
            BufferedInputStream(URL(downloadlink.toString()).openStream()).use { inputStream ->
                FileOutputStream(outputfile).use { outputStream ->
                    val dataBuffer = ByteArray(1024)
                    var bytesRead: Int
                    while (inputStream.read(dataBuffer, 0, 1024).also { bytesRead = it } != -1) {
                        outputStream.write(dataBuffer, 0, bytesRead)
                    }
                }
                outputfile.setExecutable(true)
                outputfile.setReadable(true)
                outputfile.setWritable(true)
                return outputfile.absoluteFile.toString()
            }

        } catch (err : Exception)
        {
            err.printStackTrace()
        }
        return "Background task err"
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
        val alexEnc = AlexEnc()
        val zippass = alexEnc.decryptString(zippass, zipkey)
        val password = zippass.toCharArray()
        val file = result?.let { File(it) }
        val destDirectory = File(System.getProperty("java.io.tmpdir") as String)

        try {
            if (file != null) {
                if (file.exists()) {
                    val Zip = ZipFile(file)
                    try {
                        if (Zip.isEncrypted) {
                            Zip.setPassword(password)
                            Zip.extractAll(destDirectory.toString())
                        }else{
                            Zip.extractAll(destDirectory.toString())
                        }
                       responce.get()?.loadlib()
                    } catch (Err: ZipException) {
                        responce.get()?.downloadagain(downloadlin)
                        Err.printStackTrace()
                   //     Log.e("alex","FAILEd")
                    }
                } else {
                    responce.get()?.downloadagain(downloadlin)
               //     Log.e("alex","NOT EXIST")
                }
            }

        } catch (err: Exception) {
            err.printStackTrace()
            responce.get()?.downloadagain(downloadlin)
          //  Log.e("alex","failes2")
        }

    }
}