package com.cjcj55.scrum_project_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class AccountsSettingsScreen extends AppCompatActivity implements View.OnClickListener{
    private Button asButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_settings_screen);
        asButton = findViewById(R.id.AccountSettingsOkButton);
        asButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AccountSettingsOkButton:
                Intent p = new Intent(getApplicationContext(),OrderScreen.class);
                startActivity(p);
        }
    }
}