package com.example.qingong2.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qingong2.LocationActivity;
import com.example.qingong2.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button locationBtn;

    private Button photoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        locationBtn = findViewById(R.id.btn_signIn_location);
        photoBtn = findViewById(R.id.btn_signIn_photo);

        locationBtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signIn_location:{
                Intent intent = new Intent(SignInActivity.this, LocationActivity.class);
                startActivity(intent);
            }

        }
    }
}
