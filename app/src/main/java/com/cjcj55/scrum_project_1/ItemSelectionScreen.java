package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.ItemselectionuiBinding;

//Screen shown after selecting a item from the menu(toppings here etc)
public class ItemSelectionScreen extends Fragment{


    private ItemselectionuiBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ItemselectionuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.placeorderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    NavHostFragment.findNavController(ItemSelectionScreen.this)
                            .navigate(R.id.action_ItemSelectionScreen_to_OrderHasBeenPlacedScreen);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}