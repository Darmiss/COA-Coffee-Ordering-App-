package com.cjcj55.scrum_project_1;

import android.os.Bundle;

import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cjcj55.scrum_project_1.databinding.ActivityMainBinding;
import com.cjcj55.scrum_project_1.objects.Coffee;
import com.cjcj55.scrum_project_1.objects.Flavor;
import com.cjcj55.scrum_project_1.objects.Topping;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    // db path can be accessed in Device File Explorer in Android Studio (bottom right of IDE).
        // The path is:  /data/data/com.cjcj55.scrum_project_1/databases/coffee.db
        // The coffee.db file can be opened to view tables with DB Browser for SQLite.
    private DatabaseHelper db;

    List<Coffee> coffeeTypes;
    List<Topping> toppingTypes;
    List<Flavor> flavorTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create single instance of DatabaseHelper.
        // Pass this to the fragments.
        db = DatabaseHelper.getInstance(this);

        coffeeTypes = DatabaseHelper.getAllCoffeeTypes(db);
        toppingTypes = DatabaseHelper.getAllToppingTypes(db);
        flavorTypes = DatabaseHelper.getAllFlavorTypes(db);

        System.out.println("-------------------------------\nCoffee:\n-------------------------------");
        for (Coffee coffee : coffeeTypes) {
            System.out.println(coffee.toString());
        }
        System.out.println("-------------------------------\nToppings:\n-------------------------------");
        for (Topping topping : toppingTypes) {
            System.out.println(topping.toString());
        }
        System.out.println("-------------------------------\nFlavors:\n-------------------------------");
        for (Flavor flavor : flavorTypes) {
            System.out.println(flavor.toString());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}