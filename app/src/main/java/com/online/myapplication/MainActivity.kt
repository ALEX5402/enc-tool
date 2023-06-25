package com.online.myapplication

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.online.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var activityMainBinding: ActivityMainBinding
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val viewmodel = ViewModelProvider(this).get(viewmodel::class.java)

        activityMainBinding.encriptbutton.setOnClickListener {

       val lenth = activityMainBinding.mainstring.text

            if (lenth?.isBlank()!!)
            {
                Toast.makeText(this,"please add text " ,Toast.LENGTH_LONG).show()
                activityMainBinding.outout.text = ""


            } else{
                Log.w("AAAA",lenth.toString())
                val alexEnc = AlexEnc()
                try {
                    val output  =   alexEnc.encryptString(lenth.toString())
                    activityMainBinding.outout.text = "Enc String is"  + output.first

                    viewmodel.enckey = output.second
                    viewmodel.encstring = output.first
                    Log.w("AAAA",output.first)
                    Log.w("AAAA",output.second)
                    val decriptedkey = alexEnc.decryptString(viewmodel.encstring,viewmodel.enckey)
                    activityMainBinding.outout2.text =  "main string is : $decriptedkey"
                }catch (err : Exception)
                {
                    err.printStackTrace()
                }
            }

        }

        activityMainBinding.pass.setOnClickListener {
            val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text",viewmodel.encstring)
             clipboardManager.setPrimaryClip(clipData)
        }
    activityMainBinding.key.setOnClickListener {
            val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text",viewmodel.enckey)
             clipboardManager.setPrimaryClip(clipData)
        }
    }
}