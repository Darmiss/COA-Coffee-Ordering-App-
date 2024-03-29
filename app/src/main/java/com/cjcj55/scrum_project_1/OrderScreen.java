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
import android.widget.ImageView;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjcj55.scrum_project_1.databinding.OrderuiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.CoffeeItem;

import java.text.DecimalFormat;

public class OrderScreen extends Fragment {

    private OrderuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =OrderuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.CustomWelcomeName);

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



        LinearLayout container = view.findViewById(R.id.container);

        for (CoffeeItemInCatalog coffeeItem : MainActivity.coffeeItemInCatalogTypes) {
//                System.out.println(coffeeItem.getName() + " " + coffeeItem.getDescription() + " " + coffeeItem.getPrice() + " " + coffeeItem.getId());
            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 0, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));
            buttonLayout.setElevation(15);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(100, 50, 100, 10);
            buttonLayout.setLayoutParams(layoutParams);

            //New LinearLayout for nameAndDescription Layout
            LinearLayout nameAndDescriptionLayout = new LinearLayout(getContext());
            nameAndDescriptionLayout.setOrientation(LinearLayout.VERTICAL);
            nameAndDescriptionLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            ));

            //New LinearLayout for priceandpic Layout
            LinearLayout priceandpic = new LinearLayout(getContext());
            priceandpic.setOrientation(LinearLayout.VERTICAL);
            priceandpic.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            ));


            ImageView coffeeImage = new ImageView(getContext());

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    switch (coffeeItem.getName()) {
                        case "Espresso":
                            coffeeImage.setImageResource(R.drawable.espresso);

                            break;
                        case "Latte":
                            coffeeImage.setImageResource(R.drawable.latte);

                            break;
                        case "Americano":
                            coffeeImage.setImageResource(R.drawable.americano);

                            break;
                        case "Cappuccino":
                            coffeeImage.setImageResource(R.drawable.cappuccino);

                            break;
                        case "Iced Coffee":
                            coffeeImage.setImageResource(R.drawable.icedcoffee);

                            break;
                        default:
                            break;
                    }

                    coffeeImage.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                    //buttonLayout.addView(coffeeImage);
                }
            });





            //All the UI design text things ;)-
            TextView coffeeName = new TextView(getContext());
            coffeeName.setText(coffeeItem.getName());
            coffeeName.setTextSize(30);
            coffeeName.setTextColor(Color.parseColor("white"));
            coffeeName.setShadowLayer(5, 0, 5, Color.BLACK);

            TextView coffeeDescription = new TextView(getContext());
            coffeeDescription.setText(coffeeItem.getDescription());
            coffeeDescription.setTextSize(16);
            coffeeDescription.setTextColor(Color.parseColor("white"));

            TextView coffeePrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            coffeePrice.setText("$" + df.format(coffeeItem.getPrice()));
            coffeePrice.setTextColor(Color.parseColor("white"));
            coffeePrice.setTextSize(30);
            coffeePrice.setShadowLayer(5, 0, 5, Color.BLACK);
            coffeePrice.setGravity(Gravity.END);
            //-

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
            );
            //Setting and adding  to the new linear layout(name+descritpion)
            params.gravity = Gravity.LEFT;
            nameAndDescriptionLayout.setLayoutParams(params);

            nameAndDescriptionLayout.addView(coffeeName);
            nameAndDescriptionLayout.addView(coffeeDescription);

            //Setting and adding  to the new linear layout(price+pic)
            params.gravity = Gravity.RIGHT;
            priceandpic.setLayoutParams(params);

            priceandpic.addView(coffeePrice);
            priceandpic.addView(coffeeImage);
            priceandpic.setGravity(Gravity.END);

            //Adding linearlayout to button layout(so clickable)
            buttonLayout.addView(nameAndDescriptionLayout);
            buttonLayout.addView(priceandpic);

            buttonLayout.setId(coffeeItem.getId());
            buttonLayout.setWeightSum(2);  //what is this

            params.gravity = Gravity.END;
            //coffeePrice.setLayoutParams(params);
            //buttonLayout.addView(coffeePrice);
            buttonLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Locate the id from the catalog
                    int cid = -1;
                    for (int i = 0; i < MainActivity.coffeeItemInCatalogTypes.size(); i++) {
                        if (MainActivity.coffeeItemInCatalogTypes.get(i).getId() == buttonLayout.getId()) {
                            cid = i;
                            break;
                        }
                    }

                    MainActivity.currentCoffee = new CoffeeItem(MainActivity.coffeeItemInCatalogTypes.get(cid));
                    NavHostFragment.findNavController(OrderScreen.this)
                            .navigate(R.id.action_OrderScreen_to_ItemSelectionScreen);
//                    System.out.println(MainActivity.currentCoffee.toString() + " ID:" + MainActivity.currentCoffee.getId());
                }
            });
            container.addView(buttonLayout);
        }

        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
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

                                NavHostFragment.findNavController(OrderScreen.this)
                                        .navigate(R.id.action_OrderScreen_to_LoginScreen);

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



        binding.OrderScreenBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OrderScreen.this)
                        .navigate(R.id.action_OrderScreen_to_CurrentOrdersScreen);
            }
        });





        binding.checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.userCart.getUserCart().isEmpty())
                {
                    MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Your Cart is Empty.");
                    messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
                }
                else {
                    NavHostFragment.findNavController(OrderScreen.this)
                            .navigate(R.id.action_OrderScreen_to_CheckoutCartScreen);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Context context = getContext();
        MainActivity.coffeeItemInCatalogTypes = MySQLDatabaseHelper.getAllActiveCoffeeTypes(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}