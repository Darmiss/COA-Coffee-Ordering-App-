package com.cjcj55.scrum_project_1;




import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        ListIterator<UserCart> iterate = transactionList.listIterator();
        LinearLayout dynamic = view.findViewById(R.id.previousOrder);
        int i = 0;
        try{
            while(iterate.hasNext() == true) {
                //Sets up the order for adding to the scroll view
                LinearLayout fCart = new LinearLayout(getContext());
                fCart.setOrientation(LinearLayout.HORIZONTAL);
                fCart.setPadding(40,20,40,20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fCart.setLayoutParams(layoutC);
                //Adds the first coffee name in the transaction
                TextView ummTest = new TextView(getContext());
                ummTest.setText(transactionList.get(i).getCoffeeAt(0).getName() + " " + transactionList.get(i).getCoffeeAt(0).getFlavorItemList().get(0).getName());
                ummTest.setTextSize(30);
                //Add the price total of the transaction
                TextView priceTest = new TextView(getContext());
                priceTest.setText(""+transactionList.get(i).getPrice());
                priceTest.setTextSize(30);
                priceTest.setGravity(Gravity.END);

                //Add it all together and test
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.LEFT;
                ummTest.setLayoutParams(params);
                fCart.addView(ummTest);

                params.gravity = Gravity.END;
                priceTest.setLayoutParams(params);
                fCart.addView(priceTest);

                dynamic.addView(fCart);
                i++;
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
