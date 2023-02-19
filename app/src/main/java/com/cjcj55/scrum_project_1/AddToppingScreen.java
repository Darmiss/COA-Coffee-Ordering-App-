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

import com.cjcj55.scrum_project_1.databinding.AddtoppinguiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

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
                MySQLDatabaseHelper.insertTopping(getNewToppingName(), getNewToppingDescription(), getNewToppingCost(), context);
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

    private double getNewToppingCost() {
        return Double.parseDouble(binding.newToppingPrice.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

