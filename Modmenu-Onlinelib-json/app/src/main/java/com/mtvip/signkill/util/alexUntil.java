package com.mtvip.signkill.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

public class alexUntil {
    private static Handler handler = new Handler();
    private static Runnable runnable;

    public static void clearlogs(final Context context) {
        runnable = new Runnable() {
            @Override
            public void run() {
                File[] files = new File(String.valueOf(context.getExternalFilesDir("/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Logs"))).listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                        int filecount = Math.toIntExact(Arrays.stream(files).count());
                        Toast.makeText(context, filecount + " Files are Removed", Toast.LENGTH_SHORT).show();
                    }
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.post(runnable); // Start the initial execution
    }

    public static void stopcleaner() {
        handler.removeCallbacks(runnable); // Stop the execution
    }

}
