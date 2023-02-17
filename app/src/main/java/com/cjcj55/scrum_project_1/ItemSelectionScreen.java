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
import com.cjcj55.scrum_project_1.objects.FlavorButton;
import com.cjcj55.scrum_project_1.objects.ToppingButton;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.order_items.ToppingItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//Screen shown after selecting a item from the menu(toppings here etc)
public class ItemSelectionScreen extends Fragment {


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
            System.out.println(toppingItem.getName() + " " + toppingItem.getDescription() + " " + toppingItem.getPrice() + " " + toppingItem.getId());

            ToppingButton buttonLayout = new ToppingButton(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 20, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(200, 50, 200, 10);
            buttonLayout.setLayoutParams(layoutParams);

            TextView toppingName = new TextView(getContext());
            toppingName.setText(toppingItem.getName());
            toppingName.setTextSize(20);
            toppingName.setTextColor(Color.parseColor("white"));

            TextView toppingPrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            toppingPrice.setText("+$" + df.format(toppingItem.getPrice()));
            toppingPrice.setTextColor(Color.parseColor("white"));
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
                    buttonLayout.setSelected(!buttonLayout.isSelected());

                    // Update the background color based on the selected state
                    buttonLayout.setBackground(ContextCompat.getDrawable(getContext(),
                            buttonLayout.isSelected() ? R.drawable.rounded_background_selected : R.drawable.rounded_background));

//                    printAllActiveToppings();
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
            FlavorButton buttonLayout = new FlavorButton(getContext());
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setPadding(40, 20, 40, 20);
            buttonLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(200, 50, 200, 10);
            buttonLayout.setLayoutParams(layoutParams);

            TextView flavorName = new TextView(getContext());
            flavorName.setText(flavorItem.getName());
            flavorName.setTextSize(20);
            flavorName.setTextColor(Color.parseColor("white"));

            TextView flavorPrice = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("0.00");
            flavorPrice.setText("+$" + df.format(flavorItem.getPrice()));
            flavorPrice.setTextColor(Color.parseColor("white"));
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

            buttonLayout.setOnClickListener(new View.OnClickListener() {
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
                    buttonLayout.setSelected(!buttonLayout.isSelected());

                    // Update the background color based on the selected state
                    buttonLayout.setBackground(ContextCompat.getDrawable(getContext(),
                            buttonLayout.isSelected() ? R.drawable.rounded_background_selected : R.drawable.rounded_background));

//                    printAllActiveFlavors();
                }
            });
            flavorContainer.addView(buttonLayout);
        }

        binding.addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ToppingItem> toppings = new ArrayList<>();
                List<FlavorItem> flavors = new ArrayList<>();

                for (int i = 0; i < binding.toppingContainer.getChildCount(); i++) {
                    ToppingButton topping = (ToppingButton) binding.toppingContainer.getChildAt(i);
                    if (topping.isSelected()) {
                        toppings.add(new ToppingItem(MainActivity.toppingItemInCatalogTypes.get(i)));
                    }
                }
                for (int i = 0; i < binding.flavorContainer.getChildCount(); i++) {
                    FlavorButton flavor = (FlavorButton) binding.flavorContainer.getChildAt(i);
                    if (flavor.isSelected()) {
                        flavors.add(new FlavorItem(MainActivity.flavorItemInCatalogTypes.get(i)));
                    }
                }
                MainActivity.currentCoffee.setToppingItemList(toppings);
                MainActivity.currentCoffee.setFlavorItemList(flavors);
                MainActivity.userCart.addCoffeeToCart(MainActivity.currentCoffee);
//                for (int i = 0; i < MainActivity.userCart.getUserCart().size(); i++) {
//                    System.out.println(MainActivity.userCart.getCoffeeAt(i).toString());
//                    for (int t = 0; t < MainActivity.userCart.getCoffeeAt(i).getToppingItemList().size(); t++) {
//                        System.out.println("\t" + MainActivity.userCart.getCoffeeAt(i).getToppingItemList().get(t).toString());
//                    }
//                    for (int f = 0; f < MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().size(); f++) {
//                        System.out.println("\t" + MainActivity.userCart.getCoffeeAt(i).getFlavorItemList().get(f).toString());
//                    }
//                }


                    NavHostFragment.findNavController(ItemSelectionScreen.this)
                            .navigate(R.id.action_ItemSelectionScreen_to_OrderScreen);
            }
        });

        binding.discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.currentCoffee = null;
                NavHostFragment.findNavController(ItemSelectionScreen.this)
                        .navigate(R.id.action_ItemSelectionScreen_to_OrderScreen);
            }
        });

    }

    public void printAllActiveToppings() {
        for (int i = 0; i < binding.toppingContainer.getChildCount(); i++) {
            ToppingButton topping = (ToppingButton) binding.toppingContainer.getChildAt(i);
            if (topping.isSelected()) {
                System.out.println(topping.getId());
            }
        }
    }

    public void printAllActiveFlavors() {
        for (int i = 0; i < binding.flavorContainer.getChildCount(); i++) {
            FlavorButton flavor = (FlavorButton) binding.flavorContainer.getChildAt(i);
            if (flavor.isSelected()) {
                System.out.println(flavor.getId());
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}