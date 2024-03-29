package com.cjcj55.scrum_project_1;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.CheckoutcartuiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//TODO:  Create an ArrayList<LinearLayout> to store all 'dynamic' LinearLayouts in an array.
// TODO:  When the user removes a coffee from the cart (a 'dynamic'), loop and check all IDs of the LinearLayouts to see which have an ID greater than the ID of the coffee being removed.
// TODO:  Remove the coffee and decrement all IDs greater than that coffee's ID.
public class CheckoutCartScreen extends Fragment {
    private CheckoutcartuiBinding binding;
    String selectedPickupTime;

    public Drawable getRoundedDrawable(int color, float radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(radius);
        return gd;
    }

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
                //cartLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));
                cartLayout.setPadding(40, 20, 40, 20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cartLayout.setLayoutParams(layoutC);
                //Add the coffee name to formatting
                TextView coffeeName = new TextView(getContext());
                coffeeName.setText(MainActivity.userCart.getCoffeeAt(i).getAmount() + "x " + MainActivity.userCart.getCoffeeAt(i).getName());
                coffeeName.setTextSize(30);
                coffeeName.setGravity(Gravity.LEFT);
                //Add the coffee price to formatting
                TextView coffeePrice = new TextView(getContext());
                DecimalFormat df = new DecimalFormat("0.00");
                coffeePrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getPrice()));
                coffeePrice.setTextSize(30);
                coffeePrice.setGravity(Gravity.END);
                //Create the button for the cart to remove the coffee as a total
                //Uhhh if I push enough buttons it'll have to work right
                Button removeBtn = new Button(getContext());
                removeBtn.setBackgroundColor(RED);
                removeBtn.setText("X");
                removeBtn.setTextColor(Color.rgb(255, 204, 203));
                removeBtn.setBackground(getRoundedDrawable(Color.RED, 20));
                removeBtn.setPadding(20, 10, 20, 10);


                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.userCart.getUserCart().remove(dynamic.getId());

                        total = calcTotal();
                        DecimalFormat df = new DecimalFormat("0.00");
                        totalView.setText("TOTAL: $" + df.format(total));
                        totalView.setTextSize(40);
                        totalView.setTextColor(BLACK);

                        // Remove the dynamic LinearLayout from the container
                        container.removeView(dynamic);

                        // Decrement the IDs of all dynamic LinearLayouts with ID greater than the one that was just removed
                        for (int j = dynamic.getId(); j < MainActivity.userCart.getUserCart().size(); j++) {
                            LinearLayout layout = container.findViewById(j + 1);
                            if (layout != null) {
                                layout.setId(j);
                            }
                        }
                    }
                });

                //Add it all to the GUI
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                coffeeName.setLayoutParams(params);

                params.gravity = Gravity.END;
                coffeePrice.setLayoutParams(params);

                params.gravity = Gravity.END;
                removeBtn.setLayoutParams(params);

                LinearLayout coffeeNameAndPrice = new LinearLayout(getContext());
                coffeeNameAndPrice.setOrientation(LinearLayout.HORIZONTAL);
                coffeeNameAndPrice.addView(coffeeName);
                coffeeNameAndPrice.addView(coffeePrice);


                cartLayout.addView(coffeeNameAndPrice);


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

                        // Add flavor name to format
                        TextView flavorName = new TextView(getContext());
                        flavorName.setText(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getName());
                        flavorName.setTextSize(20);

                        // Add flavor price to format
                        TextView flavorPrice = new TextView(getContext());
                        flavorPrice.setText("+" + df.format(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice()));
                        flavorPrice.setTextSize(20);
                        flavorPrice.setGravity(Gravity.RIGHT);

                        // Add the formatting to the container
                        cartLayoutF.addView(flavorName);

                        Space space = new Space(getContext());
                        space.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1f)); // This will push the flavorPrice to the right side
                        cartLayoutF.addView(space);

                        cartLayoutF.addView(flavorPrice);


                        cartLayout.addView(cartLayoutF);

                        total = total + MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice();
                    }

                } catch (NullPointerException e) {
//                    System.out.println("Empty Flavor List");
                }
                //Add Toppings calculation to total
                try {
                    for (int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getToppingItemList().size(); j++) {
                        LinearLayout cartLayoutT = new LinearLayout(getContext());
                        cartLayoutT.setOrientation(LinearLayout.HORIZONTAL);
                        cartLayoutT.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        cartLayoutT.setLayoutParams(layoutT);

                        //Add toppings name and price to format
                        TextView toppingsName = new TextView(getContext());
                        toppingsName.setText(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getName());
                        toppingsName.setTextSize(20);

                        TextView toppingsPrice = new TextView(getContext());
                        toppingsPrice.setText("+" + df.format(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice()));
                        toppingsPrice.setTextSize(20);
                        toppingsPrice.setGravity(Gravity.RIGHT);

                        //Add the formatting to the container
                        cartLayoutT.addView(toppingsName);

                        // Add a space between the toppings name and the price
                        Space space = new Space(getContext());
                        space.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1f));
                        cartLayoutT.addView(space);

                        cartLayoutT.addView(toppingsPrice);

                        cartLayout.addView(cartLayoutT);
                        total = total + MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice();
                    }
                }catch(NullPointerException e){
//                    System.out.println("No toppings ");
                }
                View line = new View(getContext());
                line.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 10));
                line.setBackgroundColor(Color.parseColor("#CCCCCC"));



                cartLayout.addView(removeBtn);





                container.addView(dynamic);
                container.addView(line);
            }
        }catch(NullPointerException e){
//            System.out.println("No Coffees NOTE THIS ERROR SHOULD NEVER BE SEEN");
        }
        DecimalFormat df = new DecimalFormat("0.00");
        totalView.setText("TOTAL: $" + df.format(calcTotal()));
        totalView.setTextSize(40);
        totalView.setTextColor(BLACK);


        binding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert entire order to database

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                int user_id = sharedPreferences.getInt("user_id", -1);

                if(calcTotal()==0)
                {
                    MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Cart is empty.");
                    messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
                }
                else if(selectedPickupTime.equals(""))
                {
                    binding.PickupTimeErrorText.setText("Pickup time required");
                   binding.PickupTimeErrorText.setVisibility(View.VISIBLE);
                }
                else {
                    MySQLDatabaseHelper.insertTransactionFromCart(user_id, MainActivity.userCart, selectedPickupTime, calcTotal(), getContext());
                    SQLiteDatabaseHelper.getInstance(getContext()).insertTransactionFromCart(user_id, MainActivity.userCart, selectedPickupTime, calcTotal());
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
        pickupTimeList.add(""); // Add an empty string as the first item
        for (int i = 0; i < 8; i++) { //Change for loop iterations to increase/decrease number of dropdown items
            SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String pickupTime = format.format(calendar.getTime());
            pickupTimeList.add(pickupTime);
            calendar.add(Calendar.MINUTE, 15);
        }
        pickupTimeList.remove(1);
        pickupTimeList.remove(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pickupTimeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnerPickupTime.setAdapter(adapter);

        binding.SpinnerPickupTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if this is a user selection or the initial selection
                    selectedPickupTime = parent.getItemAtPosition(position).toString();
                    if(!selectedPickupTime.equals(""))
                    {
                        binding.PickupTimeErrorText.setVisibility(View.INVISIBLE);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // handle nothing selected
                MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Please select a pickup time");
                messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
            }
        });




















    }

    public double calcTotal() {
        double total = 0.0;
        for (int c = 0; c < MainActivity.userCart.getUserCart().size(); c++) {
            double coffeeCost = MainActivity.userCart.getCoffeeAt(c).getPrice();

            for (int f = 0; f < MainActivity.userCart.getCoffeeAt(c).getFlavorItemList().size(); f++) {
                coffeeCost += MainActivity.userCart.getCoffeeAt(c).getFlavorItemList().get(f).getPrice();
            }

            for (int t = 0; t < MainActivity.userCart.getCoffeeAt(c).getToppingItemList().size(); t++) {
                coffeeCost += MainActivity.userCart.getCoffeeAt(c).getToppingItemList().get(t).getPrice();
            }
            coffeeCost *= MainActivity.userCart.getCoffeeAt(c).getAmount();
            total += coffeeCost;
        }
        return total;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
