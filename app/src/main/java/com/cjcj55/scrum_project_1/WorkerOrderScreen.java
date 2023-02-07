package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WorkerOrderScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workescreenui);
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

