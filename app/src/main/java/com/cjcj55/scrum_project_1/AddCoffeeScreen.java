package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AddcoffeeuiBinding;

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

        binding.AddCBackToSysAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddCoffeeScreen.this)
                        .navigate(R.id.action_AddCoffeeScreen_to_SysAdminScreen);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}




