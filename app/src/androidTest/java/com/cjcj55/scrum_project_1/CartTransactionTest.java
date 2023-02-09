package com.cjcj55.scrum_project_1;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cjcj55.scrum_project_1.db.DatabaseHelper;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;
import com.cjcj55.scrum_project_1.objects.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.order_items.ToppingItem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CartTransactionTest {
    public List<CoffeeItemInCatalog> coffeeItemInCatalogTypes;
    public List<ToppingItemInCatalog> toppingItemInCatalogTypes;
    public List<FlavorItemInCatalog> flavorItemInCatalogTypes;

    @Before
    public void initData() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        coffeeItemInCatalogTypes = DatabaseHelper.getAllActiveCoffeeTypes(dbHelper);
        toppingItemInCatalogTypes = DatabaseHelper.getAllActiveToppingTypes(dbHelper);
        flavorItemInCatalogTypes = DatabaseHelper.getAllActiveFlavorTypes(dbHelper);
    }

    @Test
    public void testAddCoffeeToCart() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();

        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(1)));

        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
    }

    @Test
    public void testRemoveCoffeeFromCart() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();

        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(1)));
        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(2)));

        // Check to make sure both coffees were added to the cart
        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(1).getId(), coffeeItemInCatalogTypes.get(2).getId());
        assertEquals(userCart.getUserCart().size(), 2);

        // Remove a coffee from the cart
        userCart.removeCoffeeFromCart(new CoffeeItem(coffeeItemInCatalogTypes.get(1)));

        // Check to make sure the coffee was removed from the cart
        assertNotEquals(userCart.getUserCart().size(), 2);
        assertEquals(userCart.getUserCart().size(), 1);
    }

    @Test
    public void testAddMultipleCoffeesToCart() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();

        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(1)));
        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(3)));
        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(0)));
        userCart.addCoffeeToCart(new CoffeeItem(coffeeItemInCatalogTypes.get(3)));

        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(1).getId(), coffeeItemInCatalogTypes.get(3).getId());
        assertEquals(userCart.getCoffeeAt(2).getId(), coffeeItemInCatalogTypes.get(0).getId());
        assertEquals(userCart.getCoffeeAt(3).getId(), coffeeItemInCatalogTypes.get(3).getId());
    }

    @Test
    public void testAddToppingsToCoffee() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();
        CoffeeItem coffeeItem = new CoffeeItem(coffeeItemInCatalogTypes.get(1));

        List<ToppingItem> toppingItemList = new ArrayList<>();
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(1)));
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(3)));

        coffeeItem.setToppingItemList(toppingItemList);

        userCart.addCoffeeToCart(coffeeItem);

        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getToppingItemList().get(0).getId(), toppingItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getToppingItemList().get(1).getId(), toppingItemInCatalogTypes.get(3).getId());
    }

    @Test
    public void testAddFlavorsToCoffee() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();
        CoffeeItem coffeeItem = new CoffeeItem(coffeeItemInCatalogTypes.get(1));

        List<FlavorItem> flavorItemList = new ArrayList<>();
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(2)));
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(0)));

        coffeeItem.setFlavorItemList(flavorItemList);

        userCart.addCoffeeToCart(coffeeItem);

        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getFlavorItemList().get(0).getId(), flavorItemInCatalogTypes.get(2).getId());
        assertEquals(userCart.getCoffeeAt(0).getFlavorItemList().get(1).getId(), flavorItemInCatalogTypes.get(0).getId());
    }

    @Test
    public void testAddToppingsAndFlavorsToCoffee() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        UserCart userCart = new UserCart();
        CoffeeItem coffeeItem = new CoffeeItem(coffeeItemInCatalogTypes.get(1));

        List<ToppingItem> toppingItemList = new ArrayList<>();
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(2)));
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(1)));

        List<FlavorItem> flavorItemList = new ArrayList<>();
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(1)));
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(3)));

        coffeeItem.setToppingItemList(toppingItemList);
        coffeeItem.setFlavorItemList(flavorItemList);

        userCart.addCoffeeToCart(coffeeItem);

        assertEquals(userCart.getCoffeeAt(0).getId(), coffeeItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getToppingItemList().get(0).getId(), toppingItemInCatalogTypes.get(2).getId());
        assertEquals(userCart.getCoffeeAt(0).getToppingItemList().get(1).getId(), toppingItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getFlavorItemList().get(0).getId(), flavorItemInCatalogTypes.get(1).getId());
        assertEquals(userCart.getCoffeeAt(0).getFlavorItemList().get(1).getId(), flavorItemInCatalogTypes.get(3).getId());
    }

    @Test
    public void testInsertTransaction() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int userId = 0;
        String pickupTime = "12:45 PM";

        UserCart userCart = new UserCart();
        CoffeeItem coffeeItem = new CoffeeItem(coffeeItemInCatalogTypes.get(2));
        userCart.addCoffeeToCart(coffeeItem);

        CoffeeItem coffeeItem1 = new CoffeeItem(coffeeItemInCatalogTypes.get(1));
        List<ToppingItem> toppingItemList = new ArrayList<>();
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(2)));
        toppingItemList.add(new ToppingItem(toppingItemInCatalogTypes.get(1)));
        List<FlavorItem> flavorItemList = new ArrayList<>();
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(1)));
        flavorItemList.add(new FlavorItem(flavorItemInCatalogTypes.get(3)));
        coffeeItem1.setToppingItemList(toppingItemList);
        coffeeItem1.setFlavorItemList(flavorItemList);
        userCart.addCoffeeToCart(coffeeItem1);

        CoffeeItem coffeeItem2 = new CoffeeItem(coffeeItemInCatalogTypes.get(2));
        userCart.addCoffeeToCart(coffeeItem2);

        // Add to database
        double totalPrice = 50.42;
        long id = dbHelper.insertTransactionFromCart(userId, userCart, pickupTime, totalPrice);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        Cursor cursor = db.query("transactions", null, "transaction_id=?", new String[]{ Long.toString(id) }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals(userId, Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
        assertEquals(pickupTime, cursor.getString(cursor.getColumnIndex("pickup_time")));
        assertEquals(totalPrice, Double.parseDouble(cursor.getString(cursor.getColumnIndex("price"))), 0.01);
        assertEquals(false, Boolean.getBoolean(cursor.getString(cursor.getColumnIndex("fulfilled"))));
        assertEquals(false, Boolean.getBoolean(cursor.getString(cursor.getColumnIndex("cancelled_by_customer"))));

        cursor.close();
        db.close();
    }
}
