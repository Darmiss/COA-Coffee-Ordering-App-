package com.cjcj55.scrum_project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.SysadminuiBinding;
import com.cjcj55.scrum_project_1.databinding.WorkescreenuiBinding;

public class SysAdminScreen extends Fragment {
    private SysadminuiBinding binding;

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            binding =SysadminuiBinding.inflate(inflater, container, false);
            return binding.getRoot();

        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            binding.AddCoffeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddCoffeeScreen);
                }
            });

            binding.RemoveCoffeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveCoffeeScreen);
                }
            });

            binding.AddFlavorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddFlavorScreen);
                }
            });

            binding.RemoveFlavorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveFlavorScreen);
                }
            });

            binding.AddToppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_AddToppingScreen);
                }
            });

            binding.RemoveToppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(SysAdminScreen.this)
                            .navigate(R.id.action_SysAdminScreen_to_RemoveToppingScreen);
                }
            });

            binding.BackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //TODO
                 //   NavHostFragment.findNavController(SysAdminScreen.this)
                     //       .navigate(R.id.action_CheckoutCartScreen_to_OrderScreen);
                }
            });



        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

    }