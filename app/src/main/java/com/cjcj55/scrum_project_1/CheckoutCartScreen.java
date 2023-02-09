package com.cjcj55.scrum_project_1;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.CheckoutcartuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import java.sql.Timestamp;

public class CheckoutCartScreen extends Fragment {
    private CheckoutcartuiBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CheckoutcartuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert entire order to database
                DatabaseHelper.getInstance(getContext()).insertTransactionFromCart(MainActivity.user, MainActivity.userCart, new Timestamp(168135474), calcTotal());

                NavHostFragment.findNavController(CheckoutCartScreen.this)
                        .navigate(R.id.action_CheckoutCartScreen_to_OrderHasBeenPlacedScreen);
            }
        });

        binding.checkoutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CheckoutCartScreen.this)
                        .navigate(R.id.action_CheckoutCartScreen_to_OrderScreen);
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
