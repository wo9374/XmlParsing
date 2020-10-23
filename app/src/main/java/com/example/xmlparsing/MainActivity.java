package com.example.xmlparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button imcs;
    Button hmims;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imcs = (Button) findViewById(R.id.imcs);
        hmims = (Button) findViewById(R.id.hmims);

        imcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Imcs_Activity.class);
                startActivity(intent);
            }
        });

        hmims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Hmims_Activity.class);
                startActivity(intent);
            }
        });
    }
}