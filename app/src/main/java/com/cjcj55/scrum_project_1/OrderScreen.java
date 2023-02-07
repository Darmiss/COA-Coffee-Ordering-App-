package com.cjcj55.scrum_project_1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.OrderuiBinding;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;

import java.util.Iterator;
import java.util.List;

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

        LinearLayout container = view.findViewById(R.id.container);

        for (CoffeeItemInCatalog coffeeItem : MainActivity.coffeeItemInCatalogTypes) {
            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 20, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);
            buttonLayout.setLayoutParams(layoutParams);

            TextView coffeeName = new TextView(getContext());
            coffeeName.setText(coffeeItem.getName());
            coffeeName.setTextSize(30);

            TextView coffeePrice = new TextView(getContext());
            coffeePrice.setText("$" + Double.toString(coffeeItem.getPrice()));
            coffeePrice.setTextSize(30);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            buttonLayout.setId(coffeeItem.getId());
            buttonLayout.setWeightSum(2);

            params.gravity = Gravity.LEFT;
            coffeeName.setLayoutParams(params);
            buttonLayout.addView(coffeeName);

            params.gravity = Gravity.END;
            coffeePrice.setLayoutParams(params);
            buttonLayout.addView(coffeePrice);

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

                    MainActivity.userCart.addCoffeeToCart(new CoffeeItem(MainActivity.coffeeItemInCatalogTypes.get(cid)));
                    NavHostFragment.findNavController(OrderScreen.this)
                            .navigate(R.id.action_OrderScreen_to_ItemSelectionScreen);
                    List<CoffeeItem> coffees = MainActivity.userCart.getUserCart();
                    Iterator<CoffeeItem> coffeeIterator = coffees.listIterator();
                    while (coffeeIterator.hasNext()) {
                        CoffeeItem c = coffeeIterator.next();
                        System.out.println(c.toString() + " ID:" + c.getId());
                    }
                }
            });
            container.addView(buttonLayout);
        }

        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OrderScreen.this)
                        .navigate(R.id.action_OrderScreen_to_LogOutScreen);
            }
        });

        binding.viewPastOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OrderScreen.this)
                        .navigate(R.id.action_OrderScreen_to_PreviousOrderScreen);
            }
        });


        binding.checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OrderScreen.this)
                        .navigate(R.id.action_OrderScreen_to_CheckoutCartScreen);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}