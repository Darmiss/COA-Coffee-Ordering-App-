package com.cjcj55.scrum_project_1;

import static com.cjcj55.scrum_project_1.LoginScreen.setAccountCreationPopup;
import static com.cjcj55.scrum_project_1.LoginScreen.setLoggedOutPopup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AccountcreationuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;



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
                Context context = getContext();
                if (checkInputs(getnewEmail(), getnewPassword(), getFirstName(), getLastName())) {
                    DatabaseHelper.getInstance(context).insertUser(getnewPassword(), getnewEmail(), getFirstName(), getLastName());
                    setAccountCreationPopup(true);  //Makes it so when going back to the login "Account created" popup is made
                    setLoggedOutPopup(false); //disables "you have been logged out" popup
                    NavHostFragment.findNavController(AccountCreationScreen.this)
                            .navigate(R.id.action_AccountCreationScreen_to_LoginScreen);

                } else {
                    Toast newToast = Toast.makeText(getContext(), "One or more field(s) left blank.",Toast.LENGTH_SHORT);
                    newToast.show();
                }
            }
        });


        binding.backtologbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAccountCreationPopup(false); //Makes it so when going back to the loginscreen the "Account created" popup wont popup
                setLoggedOutPopup(false); //Likewise
                NavHostFragment.findNavController(AccountCreationScreen.this)
                        .navigate(R.id.action_AccountCreationScreen_to_LoginScreen);

            }
        });

    }
        private String getnewEmail () {
            return binding.editTextTextNewEmailAddress.getText().toString();
        }
        private String getnewPassword () {
            return binding.editTextTextNewPassword.getText().toString();
        }
        private String getFirstName () {
            return binding.editTextTextFirstName.getText().toString();
        }
        private String getLastName () {
            return binding.editTextTextLastName.getText().toString();
        }

        private boolean checkInputs (String e, String p, String f, String l)
        {
            boolean check = true;
            if (e.isBlank() || p.isBlank() || f.isBlank() || l.isBlank()) {
                check = false;
            }
            return check;
        }
        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }


}



