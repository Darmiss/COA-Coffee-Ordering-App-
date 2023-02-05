package com.cjcj55.scrum_project_1;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.cjcj55.scrum_project_1.databinding.OrderhasbeenplaceduiBinding;


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
