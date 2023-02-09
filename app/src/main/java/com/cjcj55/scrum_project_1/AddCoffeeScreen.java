package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AddcoffeeuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

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
                DatabaseHelper.getInstance(context).insertCoffee(getNewCoffeeName(), getNewCoffeeDescription(), getNewCoffeeCost());

                Toast newToast = Toast.makeText(getContext(), "Coffee successfully added to catalog!",Toast.LENGTH_SHORT);
                newToast.show();

                MainActivity.coffeeItemInCatalogTypes = DatabaseHelper.getAllActiveCoffeeTypes(DatabaseHelper.getInstance(context));
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

    private double getNewCoffeeCost() {
        return Double.parseDouble(binding.newCoffeePrice.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}




