package com.example.pranjalkaler.paleen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button Start = findViewById(R.id.submit);
        Name = findViewById(R.id.Name);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PalindromeGame.class);
                Name.setActivated(false);
                if(Name.getText().toString().equals(""))
                    Snackbar.make(view, "Please Enter your Name", Snackbar.LENGTH_SHORT).show();
                else {
                    startActivity(intent);
                    Name.setText("");
                }
            }
        });
    }
}

