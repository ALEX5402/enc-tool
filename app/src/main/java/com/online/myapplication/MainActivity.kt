package com.online.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.online.myapplication.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val viewmodel = ViewModelProvider(this).get(viewmodel::class.java)





        activityMainBinding.mainstring.isTextInputLayoutFocusedRectEnabled
            .apply {
                activityMainBinding.mainstring.text .let {
                    viewmodel.mainstring = it.toString()
                    Log.w("jajaj",viewmodel.mainstring)
                }
            }


        activityMainBinding.encriptbutton.setOnClickListener {

       val lenth = activityMainBinding.mainstring.text
            if (lenth?.isBlank()!!)
            {
                Toast.makeText(this,"please add text " ,Toast.LENGTH_LONG).show()
                activityMainBinding.outout.text = ""
            } else{

                val alexEnc = AlexEnc()
                try {
                    val output  =   alexEnc.encryptString(viewmodel.mainstring)
                    activityMainBinding.outout.text = "Enc String is"  + output.first
                    activityMainBinding.outout.text = "Enc Key is"  +  output.second
                    viewmodel.enckey = output.second
                    viewmodel.encstring = output.first
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