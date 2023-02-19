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
import com.cjcj55.scrum_project_1.databinding.AccountcreationuiBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
                if (checkInputs(getUsername(), getnewEmail(), getnewPassword(), getFirstName(), getLastName())) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "http://" + MainActivity.LOCAL_IP + "/coffeeorderingappserver/register.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String success = jsonObject.getString("success");
                                        if (success.equals("1")) {

                                            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "User could not register", Toast.LENGTH_SHORT).show();
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
                                params.put("username", getUsername());
                                params.put("pass", getnewPassword());
                                params.put("email", getnewEmail());
                                params.put("firstName", getFirstName());
                                params.put("lastName", getLastName());
                                return params;
                            }
                        };

                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(stringRequest);

                    NavHostFragment.findNavController(AccountCreationScreen.this)
                            .navigate(R.id.action_AccountCreationScreen_to_LoginScreen);

                } else {
                    if(getFirstName().isBlank())
                    {
                        binding.firstnameErrorText.setText("This Field is Required");
                        binding.firstnameErrorText.setVisibility(view.VISIBLE);
                    }
                    else
                    {
                        binding.firstnameErrorText.setVisibility(view.INVISIBLE);
                    }
                    if(getLastName().isBlank())
                    {
                        binding.lastnameErrorText.setText("This Field is Required");
                        binding.lastnameErrorText.setVisibility(view.VISIBLE);
                    }
                    else
                    {
                        binding.lastnameErrorText.setVisibility(view.INVISIBLE);
                    }
                    if(getnewEmail().isBlank())
                    {
                        binding.emailErrorText.setText("This Field is Required");
                        binding.emailErrorText.setVisibility(view.VISIBLE);
                    }
                    else if(!getnewEmail().contains("@") || !getnewEmail().contains("."))
                    {
                        binding.emailErrorText.setText("Invalid Email Format");
                        binding.emailErrorText.setVisibility(view.VISIBLE);
                    }
                    else
                    {
                        binding.emailErrorText.setVisibility(view.INVISIBLE);
                    }
                    if(getUsername().isBlank())
                    {
                        binding.usernameErrorText.setText("This Field is Required");
                        binding.usernameErrorText.setVisibility(view.VISIBLE);
                    }
                    else
                    {
                        binding.usernameErrorText.setVisibility(view.INVISIBLE);
                    }
                    if(getnewPassword().isBlank())
                    {
                        binding.passwordErrorText.setText("This Field is Required");
                        binding.passwordErrorText.setVisibility(view.VISIBLE);
                    }
                    else if(false/*getnewPassword().length < 7 */)
                    {
                        //poopy(add in password requirments around here(remove false)
                        //binding.passwordErrorText.setText("Invalid Password Format");
                        //binding.passwordErrorText.setVisibility(view.VISIBLE);
                    }
                    else
                    {
                        binding.passwordErrorText.setVisibility(view.INVISIBLE);
                    }
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





        private String getUsername() {
            return binding.editTextTextUsername.getText().toString();
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




        private boolean checkInputs (String u, String e, String p, String f, String l)
        {
            boolean check=true;
            if (u.isBlank() || e.isBlank() || p.isBlank() || f.isBlank() || l.isBlank() || e.contains(" ") || !e.contains("@") || !e.contains(".") || p.contains(" ")) {
                check = false;
            }
            else{
                check = true;
            }
            return check;
        }
        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }


}



