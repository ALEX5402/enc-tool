package com.mtvip.signkill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mtvip.signkill.initialise.initmanager;

public class MainActivity extends AppCompatActivity

{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initmanager.Startservice(this);
    }
    

}
