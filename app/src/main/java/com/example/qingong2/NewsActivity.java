package com.example.qingong2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qingong2.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.d("22222555","hsdkjfhsk");

        Toolbar toolbar_title=(Toolbar)findViewById(R.id.news_title_toolbar);
        setSupportActionBar(toolbar_title);
        toolbar_title.setNavigationIcon(R.drawable.icon_friends);
        toolbar_title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsActivity.this,"icon",Toast.LENGTH_LONG).show();
            }
        });

        Toolbar toolbar_content=(Toolbar)findViewById(R.id.news_content_toolbar);
        setSupportActionBar(toolbar_content);
        /*toolbar_content.setNavigationIcon(R.drawable.icon_friends);
        toolbar_content.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsActivity.this,"icon",Toast.LENGTH_LONG).show();
            }
        });*/




    }
}
