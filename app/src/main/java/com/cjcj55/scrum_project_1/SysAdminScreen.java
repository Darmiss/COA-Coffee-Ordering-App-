package com.cjcj55.scrum_project_1;

import static com.cjcj55.scrum_project_1.LoginScreen.setAccountCreationPopup;
import static com.cjcj55.scrum_project_1.LoginScreen.setLoggedOutPopup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjcj55.scrum_project_1.databinding.SysadminuiBinding;
import com.cjcj55.scrum_project_1.objects.UserCart;

public class SysAdminScreen extends Fragment {
    private SysadminuiBinding binding;

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            binding =SysadminuiBinding.inflate(inflater, container, false);
            return binding.getRoot();

        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            NavController navController = Navigation.findNavController(view);
            binding.AddCoffeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddCoffeeScreen);
                }
            });

            binding.RemoveCoffeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveCoffeeScreen);
                }
            });

            binding.AddFlavorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddFlavorScreen);
                }
            });

            binding.RemoveFlavorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveFlavorScreen);
                }
            });

            binding.AddToppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddToppingScreen);
                }
            });

            binding.RemoveToppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveToppingScreen);
                }
            });

            binding.BacktoMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getContext();

                    MainActivity.userCart = new UserCart();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "http://" + MainActivity.LOCAL_IP + "/logout.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    setLoggedOutPopup(true); //makes it so when going back to login screen, logged out popup popups
                                    setAccountCreationPopup(false); //disables account creation popup

                                    SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                                    editor.clear();
                                    editor.apply();

                                    NavHostFragment.findNavController(SysAdminScreen.this)
                                            .navigate(R.id.action_SysAdminScreen_to_LoginScreen);

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(stringRequest);
                }
            });

        }







        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }


    }