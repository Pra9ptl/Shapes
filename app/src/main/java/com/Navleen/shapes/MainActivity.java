package com.Navleen.shapes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goShapes(View view) {
        Intent intent = new Intent(this.getApplicationContext(), SideCounter.class);
        startActivity(intent);
    }

    public void gorotate(View view) {
        Intent intent = new Intent(this.getApplicationContext(), RotatingShape.class);
        startActivity(intent);
    }

    public void goRandom(View view) {
        Random random = new Random();
        int r = random.nextInt(2);

        if(r == 0){
            Intent intent = new Intent(this.getApplicationContext(), SideCounter.class);
            startActivity(intent);
        }
        else if(r == 1){
            Intent intent = new Intent(this.getApplicationContext(), RotatingShape.class);
            startActivity(intent);
        }
    }
}
