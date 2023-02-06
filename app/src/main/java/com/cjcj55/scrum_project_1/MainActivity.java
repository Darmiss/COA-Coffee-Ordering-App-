package com.cjcj55.scrum_project_1;

import android.content.Intent;
import android.os.Bundle;


import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cjcj55.scrum_project_1.databinding.ActivityMainBinding;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;

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

    // 3 lists to store all coffee, topping, and flavor types from database
    public List<CoffeeItemInCatalog> coffeeItemInCatalogTypes;
    public List<ToppingItemInCatalog> toppingItemInCatalogTypes;
    public List<FlavorItemInCatalog> flavorItemInCatalogTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create single instance of DatabaseHelper.
        // Pass this to the fragments.
        db = DatabaseHelper.getInstance(this);

        coffeeItemInCatalogTypes = DatabaseHelper.getAllCoffeeTypes(db);
        toppingItemInCatalogTypes = DatabaseHelper.getAllToppingTypes(db);
        flavorItemInCatalogTypes = DatabaseHelper.getAllFlavorTypes(db);

        System.out.println("-------------------------------\nCoffee:\n-------------------------------");
        for (CoffeeItemInCatalog coffeeItemInCatalog : coffeeItemInCatalogTypes) {
            System.out.println(coffeeItemInCatalog.toString());
        }
        System.out.println("-------------------------------\nToppings:\n-------------------------------");
        for (ToppingItemInCatalog toppingItemInCatalog : toppingItemInCatalogTypes) {
            System.out.println(toppingItemInCatalog.toString());
        }
        System.out.println("-------------------------------\nFlavors:\n-------------------------------");
        for (FlavorItemInCatalog flavorItemInCatalog : flavorItemInCatalogTypes) {
            System.out.println(flavorItemInCatalog.toString());
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
            Intent intent= new Intent(MainActivity.this, AccountsSettingsScreen.class);
            startActivity(intent);
            return true;
        }
        else
            if(id == R.id.logout){
                Intent intent= new Intent(MainActivity.this, Logout.class);
                startActivity(intent);
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