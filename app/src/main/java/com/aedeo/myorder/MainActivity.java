package com.aedeo.myorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView outputTextView;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextView = (TextView) findViewById(R.id.text);
        listView=(ListView) findViewById(R.id.listView);


        Async a = new Async(outputTextView, listView);
        a.execute();
    }
}