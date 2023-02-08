package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.LoginuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import java.util.Objects;

public class LoginScreen extends Fragment {
    private LoginuiBinding binding;
    private static boolean popupaccountcreation = false;  //Used in AccountCreationScreen
    private static boolean popuploggedout= false; //Used in Order Screen when clicking log out
    public static void setAccountCreationPopup(boolean set) //Used in AccountCreationScreen
    {
        popupaccountcreation=set;
    }

    public static void setLoggedOutPopup(boolean b)
    {
        popuploggedout=b;
    }
    @Override
    public View onCreateView(



            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        MainActivity.user = -1;
        System.out.println("User now " + MainActivity.user);

        if(popupaccountcreation) { //shows the popup message "Account created" if flag set in accountcreationscreen
            MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Account Successfully Created");
            messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
        }
        if(popuploggedout)
        {
            MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("You have been logged out.");
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