package com.cjcj55.scrum_project_1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cjcj55.scrum_project_1.db.MySQLDatabaseHelper;
import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AWSDatabaseTest {
    @Test
    public void testConnectionToRemoteWebServer() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void testUserLoginPositive() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertUser("positive$Passwored4!9", "pawsitive@gmail.com", "Pawsitive", "Doggie");

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "positive$Passwored4!9" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("positive$Passwored4!9", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("pawsitive@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("Pawsitive", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("Doggie", cursor.getString(cursor.getColumnIndex("lastName")));

        cursor.close();

        db.close();
    }

    @Test
    public void testUserLoginNegative() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertUser( "negativePassword145", "negativeUserTestAcct@gmail.com", "NegativeFirstName", "NegativeLastName");

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "negativePassword145" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("negativePassword145", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("negativeUserTestAcct@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("NegativeFirstName", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("NegativeLastName", cursor.getString(cursor.getColumnIndex("lastName")));
        cursor.close();

        db.close();
    }

    @Test
    public void testCoffeeInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertCoffee("Coffee to Add", "Description to Add", 1.99);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("coffee", null, "name=?", new String[]{ "Coffee to Add" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Coffee to Add", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals(1.99, cursor.getDouble(cursor.getColumnIndex("price")), 0.01);

        cursor.close();
        db.close();
    }

    @Test
    public void testCoffeeRemoval() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to database
        long id = dbHelper.insertCoffee("Coffee to Delete", "Desc to Delete", 4.99);

        // Verify tuple was added to database
        assertNotEquals(-1, id);

        // Remove the test tuple
        int rowsDeleted = dbHelper.deleteCoffee("Coffee to Delete");

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        Cursor cursor = db.query("coffee", null, "name=?", new String[]{ "Coffee to Delete" }, null, null, null);
        assertFalse(cursor.moveToFirst());
        cursor.close();

        db.close();
    }

    @Test
    public void testToppingInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertTopping("Topping to Add", "Description to Add", 0.59);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("toppings", null, "name=?", new String[]{ "Topping to Add" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Topping to Add", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description to Add", cursor.getString(cursor.getColumnIndex("description")));
        assertEquals(0.59, cursor.getDouble(cursor.getColumnIndex("price")), 0.01);

        cursor.close();
        db.close();
    }

    @Test
    public void testToppingRemoval() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to database
        long id = dbHelper.insertTopping("Topping to Remove", "Descorpton", 0.42);

        // Verify tuple was added to database
        assertNotEquals(-1, id);

        // Remove the test tuple
        int rowsDeleted = dbHelper.deleteTopping("Topping to Remove");

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        Cursor cursor = db.query("toppings", null, "name=?", new String[]{ "Topping to Remove" }, null, null, null);
        assertFalse(cursor.moveToFirst());
        cursor.close();

        db.close();
    }

    @Test
    public void testFlavorInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertFlavor("Flavor to Add", "Description to Add", 0.99);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("flavors", null, "name=?", new String[]{ "Flavor to Add" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Flavor to Add", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description to Add", cursor.getString(cursor.getColumnIndex("description")));
        assertEquals(0.99, cursor.getDouble(cursor.getColumnIndex("price")), 0.01);

        cursor.close();
        db.close();
    }

    @Test
    public void testFlavorRemoval() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = dbHelper.insertCoffee("Flavor to Delete", "Descwiption hjaha", 0.99);
        assertNotEquals(-1, id);
        int rowsDeleted = dbHelper.deleteCoffee("Flavor to Delete");
        assertEquals(1, rowsDeleted);
        Cursor cursor = db.query("flavors", null, "name=?", new String[]{ "Flavor to Delete" }, null, null, null);
        assertFalse(cursor.moveToFirst());
        cursor.close();

        db.close();
    }

    @Test
    public void testUserRegistration() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = dbHelper.insertUser("testingInsertionPass", "testingInsertion@gmail.com", "testingInsertionFirstName", "testingInsertionLastName");
        assertNotEquals(-1, id);
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "testingInsertionPass" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("testingInsertionPass", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("testingInsertion@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("testingInsertionFirstName", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("testingInsertionLastName", cursor.getString(cursor.getColumnIndex("lastName")));
        cursor.close();
        db.close();
    }
}