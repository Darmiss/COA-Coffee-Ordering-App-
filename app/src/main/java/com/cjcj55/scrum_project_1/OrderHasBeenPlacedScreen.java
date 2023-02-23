package com.cjcj55.scrum_project_1;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.OrderhasbeenplaceduiBinding;

import java.text.DecimalFormat;

//Thing
//Scene after you have placed an order showcasing details
public class OrderHasBeenPlacedScreen extends Fragment {


    private OrderhasbeenplaceduiBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = OrderhasbeenplaceduiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO
        double total = 0;
        LinearLayout container = view.findViewById(R.id.recieptContainer);
        try{
            for(int i = 0; i < MainActivity.userCart.getUserCart().size(); i++) {
                LinearLayout recLayout = new LinearLayout(getContext());
                recLayout.setOrientation(LinearLayout.VERTICAL);
                recLayout.setPadding(40, 20, 40, 20);
                ViewGroup.LayoutParams layoutP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                recLayout.setLayoutParams(layoutP);


                LinearLayout horizontalLayout = new LinearLayout(getContext());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                TextView coffeeName = new TextView(getContext());
                coffeeName.setText(MainActivity.userCart.getCoffeeAt(i).getAmount() + "x " + MainActivity.userCart.getCoffeeAt(i).getName());
                coffeeName.setTextSize(30);
                coffeeName.setTextColor(BLACK);
                coffeeName.setGravity(Gravity.LEFT);
                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                nameParams.weight = 1.0f;
                coffeeName.setLayoutParams(nameParams);
                horizontalLayout.addView(coffeeName);


                TextView coffeePrice = new TextView(getContext());
                DecimalFormat df = new DecimalFormat("0.00");
                coffeePrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getPrice()));
                coffeePrice.setTextSize(30);
                coffeePrice.setTextColor(GREEN);
                coffeePrice.setGravity(Gravity.END);
                LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                priceParams.weight = 0.5f;
                coffeePrice.setLayoutParams(priceParams);
                horizontalLayout.addView(coffeePrice);


                recLayout.addView(horizontalLayout);

                container.addView(recLayout);
                total = total + MainActivity.userCart.getCoffeeAt(i).getPrice() * MainActivity.userCart.getCoffeeAt(i).getAmount();
                //This will
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
                        flavorName.setTextColor(BLACK);

                        // Add flavor price to format
                        TextView flavorPrice = new TextView(getContext());
                        flavorPrice.setText("+" + df.format(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice()));
                        flavorPrice.setTextSize(20);
                        flavorPrice.setTextColor(GREEN);
                        flavorPrice.setGravity(Gravity.RIGHT);

                        // Add the formatting to the container
                        cartLayoutF.addView(flavorName);

                        Space space = new Space(getContext());
                        space.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1f)); // This will push the flavorPrice to the right side
                        cartLayoutF.addView(space);

                        cartLayoutF.addView(flavorPrice);


                        recLayout.addView(cartLayoutF);

                        total = total + MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice() * MainActivity.userCart.getCoffeeAt(i).getAmount();
                    }
                } catch(NullPointerException e){
//                    System.out.println("Flavor list is empty");
                }
                try{
                    for(int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getToppingItemList().size(); j++){
                        LinearLayout cartLayoutT = new LinearLayout(getContext());
                        cartLayoutT.setOrientation(LinearLayout.HORIZONTAL);
                        cartLayoutT.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        cartLayoutT.setLayoutParams(layoutT);

                        //Add toppings name and price to format
                        TextView toppingsName = new TextView(getContext());
                        toppingsName.setText(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getName());
                        toppingsName.setTextSize(20);
                        toppingsName.setTextColor(BLACK);

                        TextView toppingsPrice = new TextView(getContext());
                        toppingsPrice.setText("+" + df.format(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice()));
                        toppingsPrice.setTextSize(20);
                        toppingsPrice.setTextColor(GREEN);
                        toppingsPrice.setGravity(Gravity.RIGHT);

                        //Add the formatting to the container
                        cartLayoutT.addView(toppingsName);

                        // Add a space between the toppings name and the price
                        Space space = new Space(getContext());
                        space.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1f));
                        cartLayoutT.addView(space);

                        cartLayoutT.addView(toppingsPrice);

                        recLayout.addView(cartLayoutT);
                        total = total + MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice() * MainActivity.userCart.getCoffeeAt(i).getAmount();
                    }
                } catch(NullPointerException e){
//                    System.out.println("Toppings list is empty");
                }
                View line = new View(getContext());
                line.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 10));
                line.setBackgroundColor(Color.parseColor("#CCCCCC"));
                container.addView(line);
            }
        } catch(NullPointerException e){
//                System.out.println("Empty cart found");
        }

        //Should add the totalView to the push
        TextView totalView = view.findViewById(R.id.totalView);
        DecimalFormat df = new DecimalFormat("0.00");
        totalView.setText("TOTAL: $" + df.format(total));
        totalView.setTextSize(40);
        totalView.setTextColor(BLACK);

        binding.OrderScreenOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ADD DATABASE INTERACTION
                //TODO ADD INTERACTION WITH THE PREVIOUS PLACED ORDER SCREEN
                MainActivity.userCart.clearCart();
               NavHostFragment.findNavController(OrderHasBeenPlacedScreen.this)
                       .navigate(R.id.action_OrderHasBeenPlacedScreen_to_OrderScreen);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
