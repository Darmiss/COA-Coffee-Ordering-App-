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

import com.cjcj55.scrum_project_1.databinding.AddflavoruiBinding;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

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
                SQLiteDatabaseHelper.getInstance(context).insertFlavor(getNewFlavorName(), getNewFlavorDescription(), getNewFlavorCost());

                Toast newToast = Toast.makeText(getContext(), "Flavor successfully added to catalog!",Toast.LENGTH_SHORT);
                newToast.show();

                MainActivity.flavorItemInCatalogTypes = SQLiteDatabaseHelper.getAllActiveFlavorTypes(SQLiteDatabaseHelper.getInstance(context));
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

    private double getNewFlavorCost() {
        return Double.parseDouble(binding.newFlavorPrice.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



