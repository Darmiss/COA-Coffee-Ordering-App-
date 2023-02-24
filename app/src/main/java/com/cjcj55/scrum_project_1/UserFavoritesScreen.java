package com.cjcj55.scrum_project_1;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cjcj55.scrum_project_1.databinding.UserfavoritesuiBinding;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;
import com.cjcj55.scrum_project_1.objects.FlavorButton;
import com.cjcj55.scrum_project_1.objects.ToppingButton;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.CoffeeItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.ToppingItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UserFavoritesScreen extends Fragment {

    private UserfavoritesuiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){
        binding = UserfavoritesuiBinding.inflate(inflator, container, false);
        return binding.getRoot();
    }
    static int idcheck;
    static int idcheck1;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);
        List<UserCart> favList = SQLiteDatabaseHelper.getInstance(getContext()).getAllFavoriteTransactionsForUser(user_id);

        LinearLayout dynamic = view.findViewById(R.id.favLayout);
        try {
            for(int i =0; i < favList.size(); i++) {
                idcheck = i;
//                System.out.println(i);
                DecimalFormat df = new DecimalFormat("0.00");
                //This will contain the orders made
                LinearLayout contain = new LinearLayout(getContext());
                contain.setOrientation(LinearLayout.VERTICAL);
                contain.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shadow));
                contain.setPadding(40,20,40,20);
                LinearLayout.LayoutParams tempparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tempparams.setMargins(10,10,10,10);
                contain.setLayoutParams(tempparams);
                contain.setId(i);
                //This part should create the title for the previous order it will contain the total and the first coffee of the list
                LinearLayout fCart = new LinearLayout(getContext());
                fCart.setOrientation(LinearLayout.HORIZONTAL);
                fCart.setPadding(40,20,40,20);
                ViewGroup.LayoutParams layoutC = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fCart.setLayoutParams(layoutC);
                //Title formatting
                TextView title = new TextView(getContext());
                title.setText(favList.get(i).getTimeOrdered());
                title.setTextSize(30);
                title.setTextColor(BLACK);
                //Total formatting
                TextView priceTotal = new TextView(getContext());
                priceTotal.setText("$ " + df.format(favList.get(i).getPrice()));
                priceTotal.setTextSize(30);
                priceTotal.setTextColor(BLACK);
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
                    for(int j = 0; j < favList.get(i).getUserCart().size(); j++){
                        idcheck1 = j;
                        LinearLayout coffeeTemp = new LinearLayout(getContext());
                        coffeeTemp.setOrientation(LinearLayout.HORIZONTAL);
                        coffeeTemp.setPadding(40,20,40,20);
                        ViewGroup.LayoutParams layoutT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        coffeeTemp.setLayoutParams(layoutT);
                        coffeeTemp.setId(j);
                        //Adds the thing to the thing
                        TextView cofName = new TextView(getContext());
                        cofName.setTextSize(20);
                        //cofName.setTextColor(WHITE);
                        cofName.setText(favList.get(i).getUserCart().get(j).getAmount() + "x " + favList.get(i).getUserCart().get(j).getName());
                        //Adds the coffee price to the thing
                        TextView cofPrice = new TextView(getContext());
                        cofPrice.setText("$ " + df.format(favList.get(i).getUserCart().get(j).getPrice()));
                        cofPrice.setTextSize(20);
                        //cofPrice.setTextColor(WHITE);
                        cofPrice.setGravity(Gravity.CENTER);
                        //Add favorite button functionality
                        Button addToCart = new Button(getContext());
                        addToCart.setText("Add to Cart");
                        addToCart.setTextColor(WHITE);
                        addToCart.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.roundedbutton));
                        addToCart.setPadding(20, 10, 20, 10);

                        //Not 100% sure this works but we will see
                        addToCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for(int i = 0; i < favList.size(); i++){
                                    MainActivity.userCart.addCoffeeToCart(favList.get(idcheck).getCoffeeAt(coffeeTemp.getId()));
                                    addToCart.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.favselect));
                                    addToCart.setText("Order added to cart");
                                }

                            }
                        });



                        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                        params.gravity = Gravity.LEFT;
                        cofName.setLayoutParams(param1);
                        coffeeTemp.addView(cofName);

                        params.gravity = Gravity.END;
                        cofPrice.setLayoutParams(params);
                        coffeeTemp.addView(cofPrice);

                        contain.addView(coffeeTemp);
                        try {
                            for (int q = 0; q < favList.get(i).getUserCart().get(j).getFlavorItemList().size(); q++) {
                                LinearLayout flavorTemp = new LinearLayout(getContext());
                                flavorTemp.setOrientation(LinearLayout.HORIZONTAL);
                                flavorTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                flavorTemp.setLayoutParams(layoutF);
                                //Adds the thing to the thing
                                TextView flavorName = new TextView(getContext());
                                flavorName.setTextSize(15);
                                //flavorName.setTextColor(WHITE);
                                flavorName.setText(favList.get(i).getUserCart().get(j).getFlavorItemList().get(q).getName());
                                //Adds the coffee price to the thing
                                TextView flavorPrice = new TextView(getContext());
                                flavorPrice.setText("$ " + favList.get(i).getUserCart().get(j).getFlavorItemList().get(q).getPrice());
                                flavorPrice.setTextSize(17);
                                //flavorPrice.setTextColor(WHITE);
                                flavorPrice.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param2.gravity = Gravity.LEFT;
                                flavorName.setLayoutParams(param2);
                                flavorTemp.addView(flavorName);

                                param2.gravity = Gravity.END;
                                flavorPrice.setLayoutParams(param2);
                                flavorTemp.addView(flavorPrice);

                                contain.addView(flavorTemp);
                            }
                        }catch(NullPointerException e){

                        }
                        try {
                            for (int q = 0; q < favList.get(i).getUserCart().get(j).getToppingItemList().size(); q++) {
                                LinearLayout topTemp = new LinearLayout(getContext());
                                topTemp.setOrientation(LinearLayout.HORIZONTAL);
                                topTemp.setPadding(40,10,40,10);
                                ViewGroup.LayoutParams layoutTT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                topTemp.setLayoutParams(layoutTT);
                                //Adds the thing to the thing
                                TextView topName = new TextView(getContext());
                                topName.setTextSize(17);
                                //topName.setTextColor(WHITE);
                                topName.setText(favList.get(i).getUserCart().get(j).getToppingItemList().get(q).getName());
                                //Adds the coffee price to the thing
                                TextView topPrice = new TextView(getContext());
                                topPrice.setText("$ " + df.format(favList.get(i).getUserCart().get(j).getToppingItemList().get(q).getPrice()));
                                topPrice.setTextSize(15);
                                //topPrice.setTextColor(WHITE);
                                topPrice.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                param3.gravity = Gravity.LEFT;
                                topName.setLayoutParams(param3);
                                topTemp.addView(topName);

                                param3.gravity = Gravity.END;
                                topPrice.setLayoutParams(param3);
                                topTemp.addView(topPrice);

                                contain.addView(topTemp);
                            }
                            contain.addView(addToCart);
                        }catch(NullPointerException e){

                        }
                    }
                }catch(NullPointerException e){

                }
                dynamic.addView(contain);
            }
        } catch(NullPointerException e){
//            System.out.println("Hello World");
        }

        //Adds functionallity to the Button On click
        binding.favBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(UserFavoritesScreen.this).navigate(R.id.action_UserFavoritesScreen_to_CurrentOrdersScreen);
            }
        });

    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}