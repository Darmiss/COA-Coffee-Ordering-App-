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

import com.cjcj55.scrum_project_1.databinding.RemovecoffeeuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;

public class RemoveCoffeeScreen extends Fragment {

    private RemovecoffeeuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =RemovecoffeeuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.removeNewCoffeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove coffee
                    Context context = getContext();
                    DatabaseHelper.getInstance(context).deleteCoffee(getCoffeeName());

                    Toast newToast = Toast.makeText(getContext(), "Coffee successfully removed from catalog!",Toast.LENGTH_SHORT);
                    newToast.show();
                    MainActivity.coffeeItemInCatalogTypes = DatabaseHelper.getAllActiveCoffeeTypes(DatabaseHelper.getInstance(context));

                NavHostFragment.findNavController(RemoveCoffeeScreen.this)
                        .navigate(R.id.action_RemoveCoffeeScreen_to_SysAdminScreen);
            }
        });

        binding.RemoveCofBackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(RemoveCoffeeScreen.this)
                        .navigate(R.id.action_RemoveCoffeeScreen_to_SysAdminScreen);

            }
        });
    }
    private String getCoffeeName() {
        return binding.removeCoffeeText.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

