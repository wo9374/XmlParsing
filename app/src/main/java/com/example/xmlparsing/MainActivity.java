package com.example.xmlparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button pullparser;
    Button docbuilder;
    Button doc_build;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullparser = (Button) findViewById(R.id.pullparser);
        docbuilder = (Button) findViewById(R.id.dom);
        doc_build = (Button) findViewById(R.id.doc_build);


        pullparser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), XmlPullParserActivity.class);
                startActivity(intent);
            }
        });

        docbuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), XMLParsingDOMExamlple.class);
                startActivity(intent);
            }
        });

        doc_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DocBuilder.class);
                startActivity(intent);
            }
        });
    }


}