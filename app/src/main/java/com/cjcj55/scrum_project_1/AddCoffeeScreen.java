package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AddcoffeeuiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

public class AddCoffeeScreen extends Fragment {


    private AddcoffeeuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =AddcoffeeuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.addNewCoffeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                if (checkInputs(getNewCoffeeName(),getNewCoffeeDescription(),getNewCoffeeCost())) {
                    MySQLDatabaseHelper.insertCoffee(getNewCoffeeName(), getNewCoffeeDescription(), Double.parseDouble(getNewCoffeeCost()), context);

                    NavHostFragment.findNavController(AddCoffeeScreen.this)
                            .navigate(R.id.action_AddCoffeeScreen_to_SysAdminScreen);
                }
                }
        });

        binding.AddCBackToSysAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddCoffeeScreen.this)
                        .navigate(R.id.action_AddCoffeeScreen_to_SysAdminScreen);
            }
        });
    }

    private String getNewCoffeeName() {
        return binding.addNewCoffee.getText().toString();
    }

    private String getNewCoffeeDescription() {
        return binding.newCoffeeDesc.getText().toString();
    }

    private String getNewCoffeeCost() {

        return binding.newCoffeePrice.getText().toString();
    }

    private boolean checkInputs (String n, String d, String c) {
        boolean check = true;
        if (n.isBlank()) {
            binding.newCoffeePriceErrorText.setText("This field is required");
            binding.newCoffeePriceErrorText.setVisibility(getView().VISIBLE);
            check = false;
        }
        if (d.isBlank()) {
            binding.newCoffeePriceErrorText.setText("This field is required");
            binding.newCoffeePriceErrorText.setVisibility(getView().VISIBLE);
            check = false;
        }
        if (c.isEmpty()) {
            binding.newCoffeePriceErrorText.setText("This field is required");
            binding.newCoffeePriceErrorText.setVisibility(getView().VISIBLE);
            check = false;
        }else {
            check = true;
        }
        return check;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}




