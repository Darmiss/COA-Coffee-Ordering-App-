package com.cjcj55.scrum_project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import static com.cjcj55.scrum_project_1.LoginScreen.setLoggedin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import com.cjcj55.scrum_project_1.databinding.ActivityLogoutBinding;


public class LogoutScreen extends Fragment {
    private ActivityLogoutBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ActivityLogoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.LogoutOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoggedin(false);
                NavHostFragment.findNavController(LogoutScreen.this)
                        .navigate(R.id.action_LogOutScreen_to_LoginScreen);
            }
        });
    }
        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }


    }

