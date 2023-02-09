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

import com.cjcj55.scrum_project_1.databinding.ViewpastorderuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;

import java.util.List;
import java.util.ListIterator;

//Get to this screen from button, will showcase past orders
public class PreviousOrderScreen extends Fragment {
    private ViewpastorderuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =ViewpastorderuiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UserCart> transactionList = DatabaseHelper.getInstance(getContext()).getAllTransactionsForUser(MainActivity.user);
        //ListIterator<UserCart> iterate = transactionList.listIterator();
        LinearLayout dynamic = view.findViewById(R.id.previousOrder);
        try{
            for(int i =0; i < transactionList.size(); i++) {
                //This will contain the orders made
                LinearLayout contain = new LinearLayout(getContext());
                contain.setOrientation(LinearLayout.VERTICAL);
                contain.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_background));
                contain.setPadding(40,20,40,20);
                contain.setId(i);
                //This part should create the title for the previous order it will contain the total and the first coffee of the list
                LinearLayout fCart = new LinearLayout(getContext());
                fCart.setOrientation(LinearLayout.HORIZONTAL);
                fCart.setPadding(40,20,40,20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fCart.setLayoutParams(layoutC);
                //Title formatting
                TextView title = new TextView(getContext());
                title.setText(transactionList.get(i).getCoffeeAt(0).getName());
                title.setTextSize(30);
                //Total formatting
                TextView priceTotal = new TextView(getContext());
                priceTotal.setText(""+transactionList.get(i).getPrice());
                priceTotal.setTextSize(30);
                priceTotal.setGravity(Gravity.END);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                title.setLayoutParams(params);
                fCart.addView(title);

                params.gravity = Gravity.END;
                priceTotal.setLayoutParams(params);
                fCart.addView(priceTotal);

                contain.addView(fCart);
                try{
                    for(int j = 0; j < transactionList.get(i).getUserCart().size(); j++){
                        try {
                            for (int q = 0; q < transactionList.get(i).getUserCart().get(j).getFlavorItemList().size(); q++) {

                            }
                        }catch(NullPointerException e){

                        }
                        try {
                            for (int q = 0; q < transactionList.get(i).getUserCart().get(j).getToppingItemList().size(); q++) {

                            }
                        }catch(NullPointerException e){

                        }
                    }
                }catch(NullPointerException e){

                }
                dynamic.addView(contain);
            }
        } catch(NullPointerException e){
            System.out.println("Hello World");
        }

        binding.pastBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PreviousOrderScreen.this)
                    .navigate(R.id.action_PreviousOrderScreen_to_OrderScreen);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
