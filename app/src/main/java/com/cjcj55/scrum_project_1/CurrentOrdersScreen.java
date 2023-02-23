package com.cjcj55.scrum_project_1;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
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
import java.util.List;
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


        TextView textView = view.findViewById(R.id.currOrderCustomWelcomeName);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);
        String username = sharedPreferences.getString("username", "");
        String firstName = sharedPreferences.getString("firstName", "");
        String lastName = sharedPreferences.getString("lastName", "");
        if(firstName.length()>=7)
        {
            firstName = firstName.substring(0, 7) + "..";
        }
        firstName+="!";
        textView.setText(firstName);

        List<UserCart> transactionList = SQLiteDatabaseHelper.getInstance(getContext()).getAllUnfulfilledTransactionsForUser(user_id);
        System.out.println("Amount of active user's orders: " + transactionList.size());
        LinearLayout dynamic = view.findViewById(R.id.currentUserOrders);

        try {
            for(int i =0; i < transactionList.size(); i++) {
//                System.out.println(i);
                DecimalFormat df = new DecimalFormat("0.00");
                //This will contain the orders made
                LinearLayout contain = new LinearLayout(getContext());
                contain.setOrientation(LinearLayout.VERTICAL);
                contain.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));
                contain.setPadding(40,20,40,20);
                LinearLayout.LayoutParams tempparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tempparams.setMargins(10,10,10,10);
                contain.setLayoutParams(tempparams);
                contain.setId(i);
                //This part should create the title for the previous order it will contain the total and the first coffee of the list
                LinearLayout fCart = new LinearLayout(getContext());
                fCart.setOrientation(LinearLayout.HORIZONTAL);
                fCart.setPadding(40,20,40,20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fCart.setLayoutParams(layoutC);
                //Title formatting
                TextView title = new TextView(getContext());
                title.setText(transactionList.get(i).getPickupTime());
                title.setTextSize(30);
                title.setTextColor(BLACK);
                //Total formatting
                TextView priceTotal = new TextView(getContext());
                priceTotal.setText("$ " + df.format(transactionList.get(i).getPrice()));
                priceTotal.setTextSize(30);
                priceTotal.setTextColor(BLACK);
                priceTotal.setGravity(Gravity.END);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                title.setLayoutParams(params);
                fCart.addView(title);

                params.gravity = Gravity.END;
                priceTotal.setLayoutParams(params);
                fCart.addView(priceTotal);

                contain.addView(fCart);

                try{
                    for(int j = 0; j < transactionList.get(i).getUserCart().size(); j++){
                        LinearLayout coffeeTemp = new LinearLayout(getContext());
                        coffeeTemp.setOrientation(LinearLayout.HORIZONTAL);
                        coffeeTemp.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        coffeeTemp.setLayoutParams(layoutT);
                        //Adds the thing to the thing
                        TextView cofName = new TextView(getContext());
                        cofName.setTextSize(20);
                        cofName.setTextColor(WHITE);
                        cofName.setText(transactionList.get(i).getUserCart().get(j).getName());
                        //Adds the coffee price to the thing
                        TextView cofPrice = new TextView(getContext());
                        cofPrice.setText("$ " + df.format(transactionList.get(i).getUserCart().get(j).getPrice()));
                        cofPrice.setTextSize(20);
                        cofPrice.setTextColor(WHITE);
                        cofPrice.setGravity(Gravity.CENTER);

                        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                        params.gravity = Gravity.LEFT;
                        cofName.setLayoutParams(param1);
                        coffeeTemp.addView(cofName);

                        params.gravity = Gravity.END;
                        cofPrice.setLayoutParams(params);
                        coffeeTemp.addView(cofPrice);


                        contain.addView(coffeeTemp);
                        try {
                            for (int q = 0; q < transactionList.get(i).getUserCart().get(j).getFlavorItemList().size(); q++) {
                                LinearLayout flavorTemp = new LinearLayout(getContext());
                                flavorTemp.setOrientation(LinearLayout.HORIZONTAL);
                                flavorTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                flavorTemp.setLayoutParams(layoutF);
                                //Adds the thing to the thing
                                TextView flavorName = new TextView(getContext());
                                flavorName.setTextSize(15);
                                flavorName.setTextColor(WHITE);
                                flavorName.setText(transactionList.get(i).getUserCart().get(j).getFlavorItemList().get(q).getName());
                                //Adds the coffee price to the thing
                                TextView flavorPrice = new TextView(getContext());
                                flavorPrice.setText("$ " + transactionList.get(i).getUserCart().get(j).getFlavorItemList().get(q).getPrice());
                                flavorPrice.setTextSize(17);
                                flavorPrice.setTextColor(WHITE);
                                flavorPrice.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param2.gravity = Gravity.LEFT;
                                flavorName.setLayoutParams(param2);
                                flavorTemp.addView(flavorName);

                                param2.gravity = Gravity.END;
                                flavorPrice.setLayoutParams(param2);
                                flavorTemp.addView(flavorPrice);

                                contain.addView(flavorTemp);
                            }
                        }catch(NullPointerException e){

                        }
                        try {
                            for (int q = 0; q < transactionList.get(i).getUserCart().get(j).getToppingItemList().size(); q++) {
                                LinearLayout topTemp = new LinearLayout(getContext());
                                topTemp.setOrientation(LinearLayout.HORIZONTAL);
                                topTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutTT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                topTemp.setLayoutParams(layoutTT);
                                //Adds the thing to the thing
                                TextView topName = new TextView(getContext());
                                topName.setTextSize(17);
                                topName.setTextColor(WHITE);
                                topName.setText(transactionList.get(i).getUserCart().get(j).getToppingItemList().get(q).getName());
                                //Adds the coffee price to the thing
                                TextView topPrice = new TextView(getContext());
                                topPrice.setText("$ " + df.format(transactionList.get(i).getUserCart().get(j).getToppingItemList().get(q).getPrice()));
                                topPrice.setTextSize(15);
                                topPrice.setTextColor(WHITE);
                                topPrice.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param3.gravity = Gravity.LEFT;
                                topName.setLayoutParams(param3);
                                topTemp.addView(topName);

                                param3.gravity = Gravity.END;
                                topPrice.setLayoutParams(param3);
                                topTemp.addView(topPrice);

                                contain.addView(topTemp);
                            }
                        }catch(NullPointerException e){

                        }
                    }
                }catch(NullPointerException e){

                }
                dynamic.addView(contain);
            }
        } catch(NullPointerException e){
//            System.out.println("Hello World");
        }

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




