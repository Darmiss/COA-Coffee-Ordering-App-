package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AccountcreationuiBinding;
import com.cjcj55.scrum_project_1.databinding.OrderuiBinding;

public class AccountCreationScreen extends Fragment {

    private AccountcreationuiBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AccountcreationuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true) //<<// Add login info in database, back to login screen
                {
                    NavHostFragment.findNavController(AccountCreationScreen.this)
                            .navigate(R.id.action_AccountCreationScreen_to_LoginScreen);
                }
            }
        });

        String newEmail = binding.editTextTextNewEmailAddress.getText().toString();
        String newPassword = binding.editTextTextNewPassword.getText().toString();
        String firstName = binding.editTextTextFirstName.getText().toString();
        String lastName = binding.editTextTextLastName.getText().toString();
        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

