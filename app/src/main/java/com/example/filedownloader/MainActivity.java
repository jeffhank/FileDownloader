package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private TextView display;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        display = findViewById(R.id.display);
    }

    public void mockFileDownloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("DOWNLOADING...");
            }
        });

        for(int downloadProgress = 0 ; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {
            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                        display.setText("");
                    }
                });
                return;
            } else {
                int finalDownloadProgress = downloadProgress;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        display.setText("Download Progress: " + finalDownloadProgress + "%");
                    }
                });
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });

    }

    public void startDownload(View view) {
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;
    }

    class ExampleRunnable implements  Runnable {
        public void run() {
            mockFileDownloader();
        }
    }


}