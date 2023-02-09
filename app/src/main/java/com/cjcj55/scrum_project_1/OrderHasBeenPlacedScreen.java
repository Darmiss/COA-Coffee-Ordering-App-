package com.cjcj55.scrum_project_1;



import static android.graphics.Color.BLACK;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.cjcj55.scrum_project_1.databinding.OrderhasbeenplaceduiBinding;

import org.w3c.dom.Text;

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
                recLayout.setOrientation(LinearLayout.HORIZONTAL);
                recLayout.setPadding(40, 20, 40, 20);
                ViewGroup.LayoutParams layoutP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                recLayout.setLayoutParams(layoutP);
                //Add the coffee name
                TextView coffeeName = new TextView(getContext());
                coffeeName.setText(MainActivity.userCart.getCoffeeAt(i).getName());
                coffeeName.setTextSize(30);
                //Add the coffee price
                TextView coffeePrice = new TextView(getContext());
                DecimalFormat df = new DecimalFormat("0.00");
                coffeePrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getPrice()));
                coffeePrice.setTextSize(30);
                coffeePrice.setGravity(Gravity.END);
                //Add the views to the container
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                coffeeName.setLayoutParams(params);
                recLayout.addView(coffeeName);

                params.gravity = Gravity.END;
                coffeePrice.setLayoutParams(params);
                recLayout.addView(coffeePrice);

                container.addView(recLayout);
                total = total + MainActivity.userCart.getCoffeeAt(i).getPrice();
                //This will
                try {
                    for (int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().size(); j++) {
                        LinearLayout recLayoutF = new LinearLayout(getContext());
                        recLayoutF.setOrientation(LinearLayout.HORIZONTAL);
                        recLayoutF.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        recLayoutF.setLayoutParams(layoutF);
                        //Add flavor name format to the container
                        TextView flavorName = new TextView(getContext());
                        flavorName.setText(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getName());
                        flavorName.setTextSize(20);
                        //Add flavor price format to container
                        TextView flavorPrice = new TextView(getContext());
                        flavorPrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice()));
                        flavorPrice.setTextSize(20);
                        flavorPrice.setGravity(Gravity.END);
                        //Add container to container
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                        params1.gravity = Gravity.LEFT;
                        flavorName.setLayoutParams(params1);
                        recLayoutF.addView(flavorName);

                        params1.gravity = Gravity.END;
                        flavorPrice.setLayoutParams(params1);
                        recLayoutF.addView(flavorPrice);
                        container.addView(recLayoutF);

                        total = total + MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(j).getPrice();
                    }
                } catch(NullPointerException e){
                    System.out.println("Flavor list is empty");
                }
                try{
                    for(int j = 0; j < MainActivity.userCart.getCoffeeAt(i).getToppingItemList().size(); j++){
                        LinearLayout recLayoutT = new LinearLayout(getContext());
                        recLayoutT.setOrientation(LinearLayout.HORIZONTAL);
                        recLayoutT.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        recLayoutT.setLayoutParams(layoutT);
                        //Add toppings name format to container
                        TextView toppingsName = new TextView(getContext());
                        toppingsName.setText(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getName());
                        toppingsName.setTextSize(20);
                        //Add toppings price format to container
                        TextView toppingsPrice = new TextView(getContext());
                        toppingsPrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice()));
                        toppingsPrice.setTextSize(20);
                        toppingsPrice.setGravity(Gravity.END);
                        //Add container to container
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                        params2.gravity = Gravity.LEFT;
                        toppingsName.setLayoutParams(params2);
                        recLayoutT.addView(toppingsName);

                        params2.gravity = Gravity.END;
                        toppingsPrice.setLayoutParams(params2);
                        recLayoutT.addView(toppingsPrice);
                        container.addView(recLayoutT);

                        total = total + MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(j).getPrice();

                    }
                } catch(NullPointerException e){
                    System.out.println("Toppings list is empty");
                }
            }
        } catch(NullPointerException e){
                System.out.println("Empty cart found");
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
