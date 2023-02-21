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

import com.cjcj55.scrum_project_1.databinding.RemoveflavoruiBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

public class RemoveFlavorScreen extends Fragment {

    private RemoveflavoruiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =RemoveflavoruiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.removeNewFlavorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove flavor
                Context context = getContext();
                MySQLDatabaseHelper.toggleDisableFlavor(getNewFlavorName(), context);

                NavHostFragment.findNavController(RemoveFlavorScreen.this)
                        .navigate(R.id.action_RemoveFlavorScreen_to_SysAdminScreen);
            }
        });

        binding.RemoveFlavorBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RemoveFlavorScreen.this)
                        .navigate(R.id.action_RemoveFlavorScreen_to_SysAdminScreen);
            }
        });
    }
    private String getNewFlavorName() {
        return binding.removeFlavorText.getText().toString();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


