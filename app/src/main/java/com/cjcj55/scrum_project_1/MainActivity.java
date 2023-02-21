package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cjcj55.scrum_project_1.databinding.ActivityMainBinding;
import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOCAL_IP = "3.131.94.20";

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    // db path can be accessed in Device File Explorer in Android Studio (bottom right of IDE).
        // The path is:  /data/data/com.cjcj55.scrum_project_1/databases/coffee.db
        // The coffee.db file can be opened to view tables with DB Browser for SQLite.
    private SQLiteDatabaseHelper db;

    // 3 lists to store all coffee, topping, and flavor types from database
    public static List<CoffeeItemInCatalog> coffeeItemInCatalogTypes;
    public static List<ToppingItemInCatalog> toppingItemInCatalogTypes;
    public static List<FlavorItemInCatalog> flavorItemInCatalogTypes;

    // CoffeeItem List to store user's shopping cart
    public static UserCart userCart;

    public static CoffeeItem currentCoffee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create single instance of DatabaseHelper.
        // Pass this to the fragments.

        db = SQLiteDatabaseHelper.getInstance(this);

        coffeeItemInCatalogTypes = MySQLDatabaseHelper.getAllActiveCoffeeTypes(MainActivity.this);
        toppingItemInCatalogTypes = MySQLDatabaseHelper.getAllActiveToppingTypes(MainActivity.this);
        flavorItemInCatalogTypes = MySQLDatabaseHelper.getAllActiveFlavorTypes(MainActivity.this);
        userCart = new UserCart();

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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
   }

}