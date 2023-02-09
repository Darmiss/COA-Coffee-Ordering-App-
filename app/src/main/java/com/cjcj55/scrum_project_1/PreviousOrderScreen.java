package com.cjcj55.scrum_project_1;




import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.ViewpastorderuiBinding;
import com.cjcj55.scrum_project_1.db.DatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;

import java.util.List;

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
//        for (int i = 0; i < transactionList.size(); i++) {
//            System.out.println("Transaction: " + transactionList.get(i).getUserCart().size());
//            for (int c = 0; c < transactionList.get(i).getUserCart().size(); c++) {
//                System.out.println(transactionList.get(i).getCoffeeAt(c).getName());
//                for (int t = 0; t < transactionList.get(i).getCoffeeAt(c).getToppingItemList().size(); t++) {
//                    System.out.println("\n" + transactionList.get(i).getCoffeeAt(c).getToppingItemList().get(t).getName());
//                }
//                for (int f = 0; f < transactionList.get(i).getCoffeeAt(c).getFlavorItemList().size(); f++) {
//                    System.out.println("\n" + transactionList.get(i).getCoffeeAt(c).getFlavorItemList().get(f).getName());
//                }
//            }
//        }

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
