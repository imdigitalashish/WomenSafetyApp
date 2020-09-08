package com.vacoder.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String USER_DETAILS = "user_details";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE_NUMBER = "mobile_number";

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        View view = getSupportActionBar().getCustomView();
        ImageButton imageButton = view.findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                startActivity(intent);

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
        }
    }
}