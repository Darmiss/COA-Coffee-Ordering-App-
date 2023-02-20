package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjcj55.scrum_project_1.databinding.LoginuiBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://" + MainActivity.LOCAL_IP + "/login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")) {
                                        String userType = jsonObject.getString("user_type");
                                        if (userType.equals("customer")) {
                                            // Login successful, navigate to home screen
                                            NavHostFragment.findNavController(LoginScreen.this)
                                                    .navigate(R.id.action_LoginScreen_to_OrderScreen);
                                        } else if (userType.equals("worker")) {
                                            // Login successful, navigate to worker screen
                                            NavHostFragment.findNavController(LoginScreen.this)
                                                    .navigate(R.id.action_LoginScreen_to_WorkerOrderScreen);
                                        } else if (userType.equals("admin")) {
                                            // Login successful, navigate to worker screen
                                            NavHostFragment.findNavController(LoginScreen.this)
                                                    .navigate(R.id.action_LoginScreen_to_SysAdminScreen);
                                        }
                                    } else {
                                        Toast.makeText(context, "Invalid username/email or password", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("username_or_email", getEmailOrUsername());
                        params.put("password", getPassword());
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(stringRequest);
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

    private String getEmailOrUsername(){
        return binding.editTextTextEmailAddress.getText().toString();
    }

    private String getPassword(){
        return binding.editTextTextPassword.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}