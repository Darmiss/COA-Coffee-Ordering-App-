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

import com.cjcj55.scrum_project_1.databinding.RemovetoppinguiBinding;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

public class RemoveToppingScreen extends Fragment {
    private RemovetoppinguiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =RemovetoppinguiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
        binding.removeNewToppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove Topping
                Context context = getContext();
                SQLiteDatabaseHelper.getInstance(context).updateToppingActive(getToppingName(), 0);
                SQLiteDatabaseHelper db = SQLiteDatabaseHelper.getInstance(getContext());
                MainActivity.toppingItemInCatalogTypes = SQLiteDatabaseHelper.getAllActiveToppingTypes(db);
                Toast newToast = Toast.makeText(getContext(), "Topping successfully removed!",Toast.LENGTH_SHORT);
                newToast.show();

                MainActivity.flavorItemInCatalogTypes = SQLiteDatabaseHelper.getAllActiveFlavorTypes(SQLiteDatabaseHelper.getInstance(context));

                NavHostFragment.findNavController(RemoveToppingScreen.this)
                        .navigate(R.id.action_RemoveToppingScreen_to_SysAdminScreen);
            }
        });
        binding.RemoveToppingBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RemoveToppingScreen.this)
                        .navigate(R.id.action_RemoveToppingScreen_to_SysAdminScreen);
            }
        });
    }
    private String getToppingName() {
        return binding.removeToppingText.getText().toString();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

