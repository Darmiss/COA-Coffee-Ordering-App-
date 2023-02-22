package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.AddflavoruiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;

public class AddFlavorScreen extends Fragment {

    private AddflavoruiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =AddflavoruiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.addNewFlavorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                if (checkInputs(getNewFlavorName(),getNewFlavorDescription(),getNewFlavorCost())) {
                    MySQLDatabaseHelper.insertFlavor(getNewFlavorName(), getNewFlavorDescription(),Double.parseDouble(getNewFlavorCost()), context);
                    NavHostFragment.findNavController(AddFlavorScreen.this)
                            .navigate(R.id.action_AddFlavorScreen_to_SysAdminScreen);
                }

            }
        });

        binding.AddFBackToSysAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFlavorScreen.this)
                        .navigate(R.id.action_AddFlavorScreen_to_SysAdminScreen);
            }
        });
    }

    private String getNewFlavorName() {
        return binding.addNewFlavor.getText().toString();
    }

    private String getNewFlavorDescription() {
        return binding.newFlavorDesc.getText().toString();
    }

    private String getNewFlavorCost() {
        return binding.newFlavorPrice.getText().toString();
    }

    private boolean checkInputs (String n, String d, String c) {
        boolean check = true;
        if (n.isBlank()) {
            binding.newFlavorNameErrorText.setText("This field is required");
            binding.newFlavorNameErrorText.setVisibility(getView().VISIBLE);
            check = false;
        } else {
            binding.newFlavorNameErrorText.setVisibility(getView().INVISIBLE);
        }
        if (d.isBlank()) {
            binding.newFlavorDescErrorText.setText("This field is required");
            binding.newFlavorDescErrorText.setVisibility(getView().VISIBLE);
            check = false;
        } else {
            binding.newFlavorDescErrorText.setVisibility(getView().INVISIBLE);
        }
        if (c.isEmpty()) {
            binding.newFlavorPriceErrorText.setText("This field is required");
            binding.newFlavorPriceErrorText.setVisibility(getView().VISIBLE);
            check = false;
        } else {
            binding.newFlavorPriceErrorText.setVisibility(getView().INVISIBLE);
        }
        return check;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



