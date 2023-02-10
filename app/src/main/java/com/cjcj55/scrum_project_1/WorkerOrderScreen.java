package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.WorkescreenuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;

import java.text.DecimalFormat;
import java.util.List;

public class WorkerOrderScreen extends Fragment {

    private WorkescreenuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =WorkescreenuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UserCart> transactionList = DatabaseHelper.getInstance(getContext()).getAllUnfulfilledTransactions();

        LinearLayout dynamic = view.findViewById(R.id.pendingOrders);

        try {
            for (int i = 0; i < transactionList.size(); i++) {
                LinearLayout orderButton = new LinearLayout(getContext());
                orderButton.setOrientation(LinearLayout.VERTICAL);
                orderButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10,10,10,10);
                orderButton.setLayoutParams(layoutParams);
                orderButton.setId(transactionList.get(i).getTransactionId());

                // Set onClick functionality to set order as "fulfilled" (completed)
                orderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper.getInstance(getContext()).fulfillTransaction(orderButton.getId());
                        orderButton.setVisibility(View.INVISIBLE);

                        // Popup MessageFragment
                        MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("Order was marked as complete!");
                        messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
                    }
                });

                // Create title
                LinearLayout orderContainer = new LinearLayout(getContext());
                orderContainer.setOrientation(LinearLayout.HORIZONTAL);
                orderContainer.setPadding(40,20,40,20);

                // Title
                TextView title = new TextView(getContext());
                title.setText("Order #" + transactionList.get(i).getTransactionId());
                title.setTextSize(30);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                title.setLayoutParams(params);
                orderContainer.addView(title);

                TextView pickupTime = new TextView(getContext());
                pickupTime.setText("Pickup: " + transactionList.get(i).getPickupTime());
                pickupTime.setTextSize(25);
                pickupTime.setGravity(Gravity.END);
                orderContainer.addView(pickupTime);

                orderButton.addView(orderContainer);

                // Display user's name (that purchased this order)
                LinearLayout userNameContainer = new LinearLayout(getContext());
                userNameContainer.setOrientation(LinearLayout.HORIZONTAL);
                userNameContainer.setPadding(60,20,40,20);

                // User's name
                TextView userName = new TextView(getContext());
                userName.setText("Ordered by: " + DatabaseHelper.getInstance(getContext()).getUsersFullName(transactionList.get(i).getUserId()));
                userName.setTextSize(25);
                userName.setGravity(Gravity.CENTER_HORIZONTAL);
                userNameContainer.addView(userName);

                orderButton.addView(userNameContainer);

                try {
                    for (int c = 0; c < transactionList.get(i).getUserCart().size(); c++) {
                        LinearLayout coffeeTemp = new LinearLayout(getContext());
                        coffeeTemp.setOrientation(LinearLayout.HORIZONTAL);
                        coffeeTemp.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        coffeeTemp.setLayoutParams(layoutT);

                        // Coffee name
                        TextView cofName = new TextView(getContext());
                        cofName.setTextSize(20);
                        cofName.setText(transactionList.get(i).getUserCart().get(c).getName());

                        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                        params.gravity = Gravity.LEFT;
                        cofName.setLayoutParams(param1);
                        coffeeTemp.addView(cofName);

                        orderButton.addView(coffeeTemp);

                        /**
                         * FLAVORS
                         */
                        try {
                            for (int f = 0; f < transactionList.get(i).getUserCart().get(c).getFlavorItemList().size(); f++) {
                                LinearLayout flavorTemp = new LinearLayout(getContext());
                                flavorTemp.setOrientation(LinearLayout.HORIZONTAL);
                                flavorTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                flavorTemp.setLayoutParams(layoutF);

                                // Flavor name
                                TextView flavorName = new TextView(getContext());
                                flavorName.setTextSize(15);
                                flavorName.setText(transactionList.get(i).getUserCart().get(c).getFlavorItemList().get(f).getName());

                                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param2.gravity = Gravity.LEFT;
                                flavorName.setLayoutParams(param2);
                                flavorTemp.addView(flavorName);

                                orderButton.addView(flavorTemp);
                            }
                        } catch (NullPointerException e) {

                        }

                        /**
                         * TOPPINGS
                         */
                        try {
                            for (int t = 0; t < transactionList.get(i).getUserCart().get(c).getToppingItemList().size(); t++) {
                                LinearLayout toppingTemp = new LinearLayout(getContext());
                                toppingTemp.setOrientation(LinearLayout.HORIZONTAL);
                                toppingTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                toppingTemp.setLayoutParams(layoutF);

                                // Flavor name
                                TextView toppingName = new TextView(getContext());
                                toppingName.setTextSize(15);
                                toppingName.setText(transactionList.get(i).getUserCart().get(c).getToppingItemList().get(t).getName());

                                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param2.gravity = Gravity.LEFT;
                                toppingName.setLayoutParams(param2);
                                toppingTemp.addView(toppingName);

                                orderButton.addView(toppingTemp);
                            }
                        } catch (NullPointerException e) {

                        }
                    }
                } catch (NullPointerException e) {

                }
                dynamic.addView(orderButton);
            }

        } catch (NullPointerException e) {

        }

        binding.BacktoMenuWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WorkerOrderScreen.this)
                        .navigate(R.id.action_WorkerOrderScreen_to_OrderScreen);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


