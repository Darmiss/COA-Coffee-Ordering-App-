package com.cjcj55.scrum_project_1;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.CheckoutcartuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CheckoutCartScreen extends Fragment {
    private CheckoutcartuiBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CheckoutcartuiBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }
    static double total;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        total = 0;
        super.onViewCreated(view, savedInstanceState);
        //Calculate the total when you open the cart
        LinearLayout container = view.findViewById(R.id.cartUI);
        TextView totalView = view.findViewById(R.id.totalCost);
        try {
            for (int i = 0; i < MainActivity.userCart.getUserCart().size(); i++) {
                int q = i;
                //using int q as a test for something else
                //Slight Test real quick
                LinearLayout dynamic = new LinearLayout(getContext());
                dynamic.setOrientation(LinearLayout.VERTICAL);
                dynamic.setPadding(40,20,40,20);
                dynamic.setId(i);


                //This already calculates the carts total so this should just be an initialize to the cart view as well
                LinearLayout cartLayout = new TableLayout(getContext());
                cartLayout.setOrientation(LinearLayout.HORIZONTAL);
                cartLayout.setPadding(40, 20, 40, 20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cartLayout.setLayoutParams(layoutC);
                //Add the coffee name to formatting
                TextView coffeeName = new TextView(getContext());
                coffeeName.setText(MainActivity.userCart.getCoffeeAt(i).getName());
                coffeeName.setTextSize(30);
                //Add the coffee price to formatting
                TextView coffeePrice = new TextView(getContext());
                DecimalFormat df = new DecimalFormat("0.00");
                coffeePrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getPrice()));
                coffeePrice.setTextSize(30);
                coffeePrice.setGravity(Gravity.CENTER);
                //Create the button for the cart to remove the coffee as a total
                //Uhhh if I push enough buttons it'll have to work right
                Button removeBtn = new Button(getContext());
                removeBtn.setBackgroundColor(RED);
                removeBtn.setText("X");
                removeBtn.setTextColor(Color.rgb(255, 204, 203));


                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.print("HI HI HI");
                        double tChange = 0;
                            tChange = tChange + MainActivity.userCart.getCoffeeAt(q).getPrice();
                        for(int w = 0; w < MainActivity.userCart.getCoffeeAt(q).getFlavorItemList().size(); w++){
                            tChange = tChange + MainActivity.userCart.getCoffeeAt(q).getFlavorItemList().get(w).getPrice();
                        }
                        for(int w = 0; w < MainActivity.userCart.getCoffeeAt(q).getToppingItemList().size(); w++){
                            tChange = tChange + MainActivity.userCart.getCoffeeAt(q).getToppingItemList().get(w).getPrice();
                        }
                        total = total - tChange;
                        DecimalFormat df = new DecimalFormat("0.00");
                        totalView.setText("TOTAL: " + df.format(total));
                        totalView.setTextSize(40);
                        totalView.setTextColor(BLACK);
                        MainActivity.userCart.getUserCart().remove(MainActivity.userCart.getCoffeeAt(q));
                        container.removeView(dynamic);


                    }
                });

                //Add it all to the GUI
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                coffeeName.setLayoutParams(params);
                cartLayout.addView(coffeeName);

                params.gravity = Gravity.CENTER;
                coffeePrice.setLayoutParams(params);
                cartLayout.addView(coffeePrice);

                params.gravity = Gravity.END;
                removeBtn.setLayoutParams(params);
                cartLayout.addView(removeBtn);
                //Add the button stuff here

                dynamic.addView(cartLayout);
                total = total + MainActivity.userCart.getCoffeeAt(i).getPrice();
                //Add Flavor calculation to total
                try {
                    for (int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().size(); j++) {
                        LinearLayout cartLayoutF = new LinearLayout(getContext());
                        cartLayoutF.setOrientation(LinearLayout.HORIZONTAL);
                        cartLayoutF.setPadding(40, 20, 40, 20);
                        ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        cartLayoutF.setLayoutParams(layoutF);
                        //Add flavor name to format
                        TextView flavorName = new TextView(getContext());
                        flavorName.setText(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getName());
                        flavorName.setTextSize(20);
                        //Add flavor price to format
                        TextView flavorPrice = new TextView(getContext());
                        flavorPrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice()));
                        flavorPrice.setTextSize(20);
                        flavorPrice.setGravity(Gravity.CENTER_HORIZONTAL);
                        //Add remove button to format

                        //Add it all to the GUI
                        params.gravity = Gravity.LEFT;
                        flavorName.setLayoutParams(params);
                        cartLayoutF.addView(flavorName);

                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        flavorPrice.setLayoutParams(params);
                        cartLayoutF.addView(flavorPrice);

                        dynamic.addView(cartLayoutF);
                        total = total + MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice();
                    }
                } catch (NullPointerException e) {
                    System.out.println("Empty Flavor List");
                }
                //Add Toppings calculation to total
                try {
                    for (int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getToppingItemList().size(); j++) {
                        LinearLayout cartLayoutT = new LinearLayout(getContext());
                        cartLayoutT.setOrientation(LinearLayout.HORIZONTAL);
                        cartLayoutT.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        cartLayoutT.setLayoutParams(layoutT);
                        //Add toppings name to format
                        TextView toppingsName = new TextView(getContext());
                        toppingsName.setText(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getName());
                        toppingsName.setTextSize(20);
                        //Add toppings price to format
                        TextView toppingsPrice = new TextView(getContext());
                        toppingsPrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice()));
                        toppingsPrice.setTextSize(20);
                        toppingsPrice.setGravity(Gravity.CENTER_HORIZONTAL);
                        //Add the button functionality
                        //Uhhh we will do this yes push more buttons

                        //Add the formatting to the container
                        params.gravity = Gravity.LEFT;
                        toppingsName.setLayoutParams(params);
                        cartLayoutT.addView(toppingsName);

                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        toppingsPrice.setLayoutParams(params);
                        cartLayoutT.addView(toppingsPrice);

                        dynamic.addView(cartLayoutT);
                        total = total + MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice();
                    }
                }catch(NullPointerException e){
                    System.out.println("No toppings ");
                }
                container.addView(dynamic);
            }
        }catch(NullPointerException e){
            System.out.println("No Coffees NOTE THIS ERROR SHOULD NEVER BE SEEN");
        }

        DecimalFormat df = new DecimalFormat("0.00");
        totalView.setText("TOTAL: " + df.format(total));
        totalView.setTextSize(40);
        totalView.setTextColor(BLACK);


        binding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert entire order to database
                DatabaseHelper.getInstance(getContext()).insertTransactionFromCart(MainActivity.user, MainActivity.userCart, new Timestamp(168135474), calcTotal());
                if(calcTotal()==0)
                {
                    MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Your Cart is now empty.");
                 messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
                }
                else {
                    NavHostFragment.findNavController(CheckoutCartScreen.this)
                            .navigate(R.id.action_CheckoutCartScreen_to_OrderHasBeenPlacedScreen);
                }
            }
        });

        binding.checkoutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CheckoutCartScreen.this)
                        .navigate(R.id.action_CheckoutCartScreen_to_OrderScreen);
            }
        });






        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) / 15) * 15);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<String> pickupTimeList= new ArrayList<>();
        for (int i = 0; i < 8; i++) { //Change for loop iterations to increase/decrease number of dropdown items
            SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String pickupTime = format.format(calendar.getTime());
            pickupTimeList.add(pickupTime);
            calendar.add(Calendar.MINUTE, 15);
        }
        pickupTimeList.remove(0);
        pickupTimeList.remove(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pickupTimeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnerPickupTime.setAdapter(adapter);

        binding.SpinnerPickupTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPickupTime = parent.getItemAtPosition(position).toString();
                //TODO: ADD THIS PICKUP TIME TO DATABASE
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // handle nothing selected
            }
        });

    }

    public double calcTotal() {
        double total = 0.0;
        for (int c = 0; c < MainActivity.userCart.getUserCart().size(); c++) {
            total += MainActivity.userCart.getCoffeeAt(c).getPrice();

            for (int f = 0; f < MainActivity.userCart.getCoffeeAt(c).getFlavorItemList().size(); f++) {
                total += MainActivity.userCart.getCoffeeAt(c).getFlavorItemList().get(f).getPrice();
            }

            for (int t = 0; t < MainActivity.userCart.getCoffeeAt(c).getToppingItemList().size(); t++) {
                total += MainActivity.userCart.getCoffeeAt(c).getToppingItemList().get(t).getPrice();
            }
        }
        return total;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
