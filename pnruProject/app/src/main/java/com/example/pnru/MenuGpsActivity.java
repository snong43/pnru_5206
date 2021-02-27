package com.example.pnru;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.ArrayList;

public class MenuGpsActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView tvLatitude,tvLongitude;
    private ArrayList<String> itemsLoccation;
    private ArrayList<String> itemsLoc;
    private static final String USER_INSERT_LOC_PHP = "http://" + UrlConstantPHP.MY_LOCAL  + "/testmysql/insertLocationData.php";
    private ArrayAdapter<String> adapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gps);

        tvLatitude = (TextView)findViewById(R.id.latitude);
        tvLongitude = (TextView)findViewById(R.id.longitude);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }



    public void getLocation(View view){
        gpsTracker = new GpsTracker(MenuGpsActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));

            // select location
     //      new MenuInsertActivity.CallDeletePHP().execute(itemsID.get(position));



        }else{
            gpsTracker.showSettingsAlert();
        }
    }






}