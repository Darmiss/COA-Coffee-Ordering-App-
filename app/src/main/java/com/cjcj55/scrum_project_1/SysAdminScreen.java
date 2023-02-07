package com.cjcj55.scrum_project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SysAdminScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysadminui);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           // case R.id.LogoutOkButton:
           //     Intent i = new Intent(getApplicationContext(),LoginScreen.class);
           //     startActivity(i);

        }
    }
}

