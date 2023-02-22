package com.cjcj55.scrum_project_1;

import static com.cjcj55.scrum_project_1.LoginScreen.setAccountCreationPopup;
import static com.cjcj55.scrum_project_1.LoginScreen.setLoggedOutPopup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.CurrentordersuiBinding;
import com.cjcj55.scrum_project_1.databinding.OrderuiBinding;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;


import static com.cjcj55.scrum_project_1.LoginScreen.setAccountCreationPopup;
import static com.cjcj55.scrum_project_1.LoginScreen.setLoggedOutPopup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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


import java.text.DecimalFormat;


public class CurrentOrdersScreen extends Fragment {

    private CurrentordersuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CurrentordersuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView textView = view.findViewById(R.id.CustomWelcomeName);
//        String customText = SQLiteDatabaseHelper.getInstance(getContext()).getUserFirstName(MainActivity.user);
//        if(customText.length()>=7)
//        {
//            customText = customText.substring(0, 7) + "..";
//        }
//        customText+="!";
//        textView.setText(customText);


        binding.currOrderLogoutBtn.setOnClickListener(new View.OnClickListener() {
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

                                NavHostFragment.findNavController(CurrentOrdersScreen.this)
                                        .navigate(R.id.action_CurrentOrdersScreen_to_LoginScreen);

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


        binding.CurrOrderMakeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CurrentOrdersScreen.this)
                        .navigate(R.id.action_CurrentOrdersScreen_to_OrderScreen);

            }
        });



        binding.CurrOrderViewFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CurrentOrdersScreen.this)
                        .navigate(R.id.action_CurrentOrdersScreen_to_UserFavoritesScreen);

            }
        });




        binding.CurrOrderViewPastOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CurrentOrdersScreen.this)
                        .navigate(R.id.action_CurrentOrdersScreen_to_PreviousOrderScreen);

            }
        });









    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}




