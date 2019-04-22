package com.chabok.chabokpush.testchabokservice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonStartService = (Button) findViewById(R.id.btnStartService);

        this.buttonStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartService","Start service button called");
                //Starting first service
                Intent launch = new Intent(MainActivity.this,FirstService.class);
                startService(launch);
            }
        });
    }
}
