package com.wam.travelplanner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wam.travelplanner.R;
import com.wam.travelplanner.utils.Debug;

public class SplashScreenActivity extends Activity {

    private WaitingThread mWaitingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (mWaitingThread == null) {
            mWaitingThread = new WaitingThread();
            mWaitingThread.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mWaitingThread != null) {
            mWaitingThread.interrupt();
        }
    }

    private class WaitingThread extends Thread {

        @Override
        public void run() {
            try {
                sleep(getResources().getInteger(R.integer.splash_screen_time));

                if (!isFinishing()) {
                    runOnUiThread(new ActivityLoader());
                }

            } catch (InterruptedException e) {
                Debug.logWarning(e.getMessage());
            }
        }
    }


    private class ActivityLoader implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(SplashScreenActivity.this, TravelPlannerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }
}
