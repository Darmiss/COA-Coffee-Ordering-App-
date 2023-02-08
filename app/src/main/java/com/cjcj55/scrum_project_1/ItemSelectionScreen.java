package com.cjcj55.scrum_project_1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.ItemselectionuiBinding;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;
import com.cjcj55.scrum_project_1.objects.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.order_items.ToppingItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        /**
         * -----------------------------------------------------
         * TOPPINGS
         * -----------------------------------------------------
         */
        LinearLayout toppingContainer = view.findViewById(R.id.toppingContainer);

        for (ToppingItemInCatalog toppingItem : MainActivity.toppingItemInCatalogTypes) {
            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 20, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);
            buttonLayout.setLayoutParams(layoutParams);

            TextView toppingName = new TextView(getContext());
            toppingName.setText(toppingItem.getName());
            toppingName.setTextSize(30);

            TextView toppingPrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            toppingPrice.setText("$" + df.format(toppingItem.getPrice()));
            toppingPrice.setTextColor(Color.parseColor("#006400"));
            toppingPrice.setTextSize(30);
            toppingPrice.setGravity(Gravity.END);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            buttonLayout.setId(toppingItem.getId());
            buttonLayout.setWeightSum(2);

            params.gravity = Gravity.LEFT;
            toppingName.setLayoutParams(params);
            buttonLayout.addView(toppingName);

            params.gravity = Gravity.END;
            toppingPrice.setLayoutParams(params);
            buttonLayout.addView(toppingPrice);

            buttonLayout.setOnClickListener(new View.OnClickListener() {
                private boolean isSelected = false;
                @Override
                public void onClick(View view) {
                    // Locate the id from the catalog
                    int tid = -1;
                    for (int i = 0; i < MainActivity.toppingItemInCatalogTypes.size(); i++) {
                        if (MainActivity.toppingItemInCatalogTypes.get(i).getId() == buttonLayout.getId()) {
                            tid = i;
                            break;
                        }
                    }

                    // Checkbox toggle this item.  handle all selected on form submit
                    // Toggle the selected state
                    isSelected = !isSelected;

                    // Update the background color based on the selected state
                    buttonLayout.setBackground(ContextCompat.getDrawable(getContext(),
                            isSelected ? R.drawable.rounded_background_selected : R.drawable.rounded_background));
                }
            });
            toppingContainer.addView(buttonLayout);
        }


        /**
         * -----------------------------------------------------
         * FLAVORS
         * -----------------------------------------------------
         */
        LinearLayout flavorContainer = view.findViewById(R.id.flavorContainer);

        for (FlavorItemInCatalog flavorItem : MainActivity.flavorItemInCatalogTypes) {
            LinearLayout buttonLayout = new LinearLayout(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 20, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);
            buttonLayout.setLayoutParams(layoutParams);

            TextView flavorName = new TextView(getContext());
            flavorName.setText(flavorItem.getName());
            flavorName.setTextSize(30);

            TextView flavorPrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            flavorPrice.setText("$" + df.format(flavorItem.getPrice()));
            flavorPrice.setTextColor(Color.parseColor("#006400"));
            flavorPrice.setTextSize(30);
            flavorPrice.setGravity(Gravity.END);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            buttonLayout.setId(flavorItem.getId());
            buttonLayout.setWeightSum(2);

            params.gravity = Gravity.LEFT;
            flavorName.setLayoutParams(params);
            buttonLayout.addView(flavorName);

            params.gravity = Gravity.END;
            flavorPrice.setLayoutParams(params);
            buttonLayout.addView(flavorPrice);

            final View.OnClickListener listener;
            buttonLayout.setOnClickListener(listener = new View.OnClickListener() {
                private boolean isSelected = false;
                @Override
                public void onClick(View view) {
                    // Locate the id from the catalog
                    int fid = -1;
                    for (int i = 0; i < MainActivity.flavorItemInCatalogTypes.size(); i++) {
                        if (MainActivity.flavorItemInCatalogTypes.get(i).getId() == buttonLayout.getId()) {
                            fid = i;
                            break;
                        }
                    }

                    // Checkbox toggle this item.  handle all selected on form submit
                    // Toggle the selected state
                    isSelected = !isSelected;

                    // Update the background color based on the selected state
                    buttonLayout.setBackground(ContextCompat.getDrawable(getContext(),
                            isSelected ? R.drawable.rounded_background_selected : R.drawable.rounded_background));
                }
            });
            flavorContainer.addView(buttonLayout);
        }

        binding.addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ToppingItem> toppings = new ArrayList<>();
                List<FlavorItem> flavors = new ArrayList<>();


                    NavHostFragment.findNavController(ItemSelectionScreen.this)
                            .navigate(R.id.action_ItemSelectionScreen_to_OrderScreen);
            }
        });

        binding.discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ItemSelectionScreen.this)
                        .navigate(R.id.action_ItemSelectionScreen_to_OrderScreen);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}