package com.example.pnru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button btnAddData;
    public Button btnShowGps;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddData = (Button)findViewById(R.id.btnInsert);
        btnShowGps = (Button)findViewById(R.id.btnGps);

        btnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), MenuInsertActivity.class);
                startActivity(intent);
            }
        });

        btnShowGps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), MenuGpsActivity.class);
                startActivity(intent);
            }
        });


    }


}