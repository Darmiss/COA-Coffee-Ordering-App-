package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AddtoppinguiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;

public class AddToppingScreen extends Fragment {


    private AddtoppinguiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddtoppinguiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.addNewToppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                if (checkInputs(getNewToppingName(),getNewToppingDescription(),getNewToppingCost())) {
                    MySQLDatabaseHelper.insertTopping(getNewToppingName(), getNewToppingDescription(), Double.parseDouble(getNewToppingCost()), context);
                    NavHostFragment.findNavController(AddToppingScreen.this)
                            .navigate(R.id.action_AddToppingScreen_to_SysAdminScreen);
                }

            }
        });

        binding.AddTBackToSysAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddToppingScreen.this)
                        .navigate(R.id.action_AddToppingScreen_to_SysAdminScreen);
            }
        });
    }

    private String getNewToppingName() {
        return binding.addNewTopping.getText().toString();
    }

    private String getNewToppingDescription() {
        return binding.newToppingDesc.getText().toString();
    }

    private String getNewToppingCost() {
        return binding.newToppingPrice.getText().toString();
    }

    private boolean checkInputs (String n, String d, String c) {
        boolean check = true;
        if (n.isBlank() || d.isBlank() || c.isEmpty()) {
            check = false;
        } else {
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

