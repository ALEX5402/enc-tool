package com.mtvip.signkill.initialise;

import android.content.Context;
import android.content.Intent;

import com.mtvip.signkill.service.Responce;

public class initmanager
{
    public static void Startservice (Context context)
    {
        try {
            context.startService(new Intent(context, Responce.class));
        }catch (Exception err)
        {
            err.printStackTrace();
        }


    }
}
