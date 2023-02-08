package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.LoginuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

public class LoginScreen extends Fragment {
    private LoginuiBinding binding;
    private static boolean popuploggedin = false;  //Used in AccountCreationScreen

    public static void setLoggedin(boolean set) //Used in AccountCreationScreen
    {
        popuploggedin=set;
    }
    @Override
    public View onCreateView(



            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        if(popuploggedin) { //shows the popup message "Account created" if flag set in accountcreationscreen
            MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Account Successfully Created");
            messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
        }


        binding = LoginuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    private String getEmail(){
        return binding.editTextTextEmailAddress.getText().toString();
    }

    private String getPassword(){
       return binding.editTextTextPassword.getText().toString();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
//                boolean check = DatabaseHelper.getInstance(context).userLogin(getEmail(),getPassword());
                boolean check = DatabaseHelper.getInstance(context).userLogin("johnjones@mail.com","coffee");
                if(check)
                {
                   NavHostFragment.findNavController(LoginScreen.this)
                            .navigate(R.id.action_LoginScreen_to_OrderScreen);
                }
                else{
                    MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Invalid Credentials");
                    messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
                }
            }
        });

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override //Will need to go to create account screen
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginScreen.this)
                      .navigate(R.id.action_LoginScreen_to_AccountCreationScreen);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}