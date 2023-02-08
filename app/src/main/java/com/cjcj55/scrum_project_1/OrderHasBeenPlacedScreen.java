package com.cjcj55.scrum_project_1;



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
        LinearLayout container = view.findViewById(R.id.recieptContainer);
        for(int i = 0; i < MainActivity.userCart.getUserCart().size(); i++) {
            LinearLayout recLayout = new LinearLayout(getContext());
            recLayout.setOrientation(LinearLayout.HORIZONTAL);
            recLayout.setPadding(40,20,40,20);
            ViewGroup.LayoutParams layoutP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            recLayout.setLayoutParams(layoutP);
            //Add the coffee name
            TextView coffeeName = new TextView(getContext());
            coffeeName.setText(MainActivity.userCart.getCoffeeAt(i).getName());
            coffeeName.setTextSize(30);

            TextView coffeePrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            coffeePrice.setText(df.format(MainActivity.userCart.getCoffeeAt(i).getPrice()));
            coffeePrice.setTextSize(30);
            coffeePrice.setGravity(Gravity.END);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            params.gravity = Gravity.LEFT;
            coffeeName.setLayoutParams(params);
            recLayout.addView(coffeeName);

            params.gravity = Gravity.END;
            coffeePrice.setLayoutParams(params);
            recLayout.addView(coffeePrice);
            container.addView(recLayout);
            //Add 2 for loops one to do all the format setting for toppings and one to do all the format settings for
        }

        binding.OrderScreenOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
