package com.vacoder.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String USER_DETAILS = "user_details";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE_NUMBER = "mobile_number";

    private static final String TAG = "MainActivity";

    private TextView tv_name;

    SharedPreferences sharedPreferences;
    public String bestProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(USER_DETAILS, MODE_PRIVATE);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(sharedPreferences.getString(USER_NAME, ""));


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        View view = getSupportActionBar().getCustomView();
        ImageButton imageButton = view.findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                startActivityForResult(intent, 4);

            }
        });


        loadData();

    }

    public void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        if (sharedPreferences.getString(USER_NAME, "").equals("")) {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Intent intent = new Intent(MainActivity.this, GetDetailsActivity.class);
            startActivity(intent);
        } else if (requestCode == 4) {
            tv_name.setText(data.getStringExtra(USER_NAME));
        }
    }

    public void alertMessage(View view) {
        LocationManager locationManager = (LocationManager)  this.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        Location location = locationManager.getLastKnownLocation(bestProvider);
        try {

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            SmsManager smsManager = SmsManager.getDefault();
            StringBuffer smsBody = new StringBuffer();
            smsBody.append("https://maps.google.com?q=");
            smsBody.append(locationGPS.getLatitude());
            smsBody.append(",");
            smsBody.append(locationGPS.getLongitude());
            Log.d("ASHISH", smsBody.toString());

        } catch (SecurityException r) {
            r.printStackTrace();
        }

    }
}