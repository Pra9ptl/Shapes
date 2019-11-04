package com.Navleen.shapes;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.ParticleEvent;
import io.particle.android.sdk.cloud.ParticleEventHandler;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class SideCounter extends AppCompatActivity {

    private final String TAG = "navleen";

    TextView answer;
    ImageView displayView;
    int correctAnswer;
    int score;

    private final String PARTICLE_USERNAME = "manubhatti2309@gmail.com";
    private final String PARTICLE_PASSWORD = "9780360325";

    private final String DEVICE_ID = "1a0029001247363333343437";

    private long subscriptionId;

    private ParticleDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_counter);

        ParticleCloudSDK.init(getApplicationContext());

        getDevices();


        displayView = findViewById(R.id.diplayView);
        answer = findViewById(R.id.answerDisplay);
        correctAnswer = -1;
        score = 0;
    }

    public void showShape(View view) {
        Random rand = new Random();
        int n = rand.nextInt(3);

        if(n == 0){
            displayView.setImageResource(R.drawable.circle);
            correctAnswer = 0;
        }
        else if(n == 1){
            displayView.setImageResource(R.drawable.triangle);
            correctAnswer = 3;
        }
        else if(n == 2){
            displayView.setImageResource(R.drawable.square);
            correctAnswer = 4;
        }
    }

    public void checkAnswer(View view) {
        getFromDevice("sid");
    }

    public void getDevices() {
        // This function runs in the background
        // It tries to connect to the Particle Cloud and get your device
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
                mDevice = particleCloud.getDevice(DEVICE_ID);
                return -1;
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Successfully got device from Cloud");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });
    }

    public void getFromDevice(String eventDD) {

        if (mDevice == null) {
            Log.d(TAG, "Cannot find device");
            return;
        }

        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

            @Override
            public Object callApi(ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                subscriptionId = ParticleCloudSDK.getCloud().subscribeToMyDevicesEvents(
                        eventDD,  // the first argument, "eventNamePrefix", is optional
                        new ParticleEventHandler() {
                            public void onEvent(String eventName, ParticleEvent event) {
                                Log.d(TAG, "Received event with payload: " + event.dataPayload);
                                //   ss = (event.dataPayload).toString();

                                runOnUiThread(new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(correctAnswer == Integer.parseInt(event.dataPayload) == true){
                                            score ++;
                                        }
                                        answer.setText(score);
                                    }
                                }));
                            }

                            public void onEventError(Exception e) {
                                Log.e(TAG, "Event error: ", e);
                            }
                        });
                return -1;
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Successfully got device data from Cloud");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });

    }

    public void goback(View view) {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
