package com.mtvip.signkill.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.mtvip.signkill.data.database
import com.mtvip.signkill.download.Downloader
import com.mtvip.signkill.enc.AlexEnc
import com.mtvip.signkill.util.alexUntil
import org.json.JSONException
import java.util.Locale


class Responce : Service(){



    override fun onCreate() {
        super.onCreate()
        Databse(this)
    }


    fun Databse (context: Context )
    {
        val alexEnc = AlexEnc()
        val status = alexEnc.decryptString("qxQ5smJt+2BMkMBxX6PR4Q==","o7MI0du63dHq4uotpxRk7A==")
        val notice = alexEnc.decryptString("Jcebe/+HCVemTV45lpmv/w==","dbhZG/590pEhDHngs97SpA==")
        val clearmode = alexEnc.decryptString("SQXGilUukhON/cc8aAsy8Q==","w1KUoqfXP0ozRPB9HfAStA==")
        val meassage = alexEnc.decryptString("CVH9zAoA9k/P7nzg1gNIqQ==","uE4lq49KHZV4gexVTyAC1w==")
        val time = alexEnc.decryptString("vmdCVAcDIIUkd6rca63L9g==","PtiNFNLSW2zoaqza3UG05Q==")
        val libs = alexEnc.decryptString("tpsTf7qiq9PQbnLds99b6A==","qiJD+NRLjgR5QOF21wUNdw==")
        val title = alexEnc.decryptString("j9UJJDqfiboPnY4WIksgtQ==","IFkwon1JRBgbaqiIKB+ZYQ==")
        val body = alexEnc.decryptString("jfSxNbrBf+nro8CFkYavDg==","fCSgCp5SBR6ayuMsYAYCDQ==")
      //  val jsondata = alexEnc.decryptString("Qf9zhTd+IgLmkY0yVu+kb+CZnRRwU+3Iw6yppFB4n25dk0bfcAdZktgEPJH6giat7T4/mmbJkbbhxJem4fzQPSeBkCdpf51fxyXRKOGt1uM=","uwDoZK8HE9VNdglpQa+xWg==")

        val jsondata = "https://raw.githubusercontent.com/ALEX5402/lib_online/main/bot9tion-testing"
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest= JsonObjectRequest(
            com.android.volley.Request.Method.GET, jsondata, null,
            { response ->
                try {
                    val database = database(
                        response.getString(status),
                        response.getString(notice),
                        response.getString(clearmode),
                        response.getString(meassage),
                        response.getString(time),
                        response.getString(libs),
                        response.getString(title),
                        response.getString(body)
                    )
                    showdialog(database,this)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {
                Toast.makeText(context,"Something Wrong Internet Error",Toast.LENGTH_LONG).show()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun showdialog (database: database , context: Context )
    {
        val noticemode = database.noticemode.toBoolean()
        val serverstatus = database.serverstatus.toBoolean()
        val dataclear = database.dataclearmode.toBoolean()
        val libmode = database.libs
        if (noticemode)
        {
           Toast.makeText(
               context,
               database.notice_title+database.notice_body,
               Toast.LENGTH_LONG)
               .show()
        }
           if(!serverstatus)
           {
               Toast.makeText(
                   context,
                   database.servermeassage + database.opentime,
                   Toast.LENGTH_LONG
               ).show()
               Thread.sleep(6000)
               System.exit(1)
           } else {
                   val Downloader = Downloader(this,libmode,"6JK4b179mZWcMHPfBtbRtBnbvCV7/uT8qNEmwODcodk=","RsrT/bzEJlGp5o2DbKQToA==")
                   Downloader.execute()
           }

            if (dataclear)
            {
                alexUntil.clearlogs(this)
            }
    }

    fun downloadagain (link:String)
    {
      //  Log.w("Alex", "Download")
        Toast.makeText(this,"download failed dowloading again ...",Toast.LENGTH_LONG ).show()
        Databse(this)
    }
    @SuppressLint("UnsafeDynamicallyLoadedCode")
    fun loadlib() {
        val files = this.cacheDir.listFiles()
        try {
            if (files != null) {
                for (file in files) {
                    val filename = file.name.lowercase(Locale.getDefault())
                    if (filename.endsWith(".so")) {
                       System.load(file.toString())
                    }
                }
            }
            if (files != null) {
                for (file in files) {
                    val filename = file.name.lowercase(Locale.getDefault())
                    if (filename.endsWith(".zip") || filename.endsWith(".so")) {
                        file.delete()
                    }
                }
            }
            Toast.makeText(this, "LIB Load Done Open Game ", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "LIB Load Done Open Game ", Toast.LENGTH_LONG).show()
        } catch (err: Exception) {
            if (files != null) {
                for (file in files) {
                    val filename = file.name.lowercase(Locale.getDefault())
                    if (filename.endsWith(".zip") || filename.endsWith(".so")) {
                        file.delete()
                    }
                }
            }
            err.printStackTrace()
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



}
