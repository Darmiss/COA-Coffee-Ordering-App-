package com.cjcj55.scrum_project_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cjcj55.scrum_project_1.db.SQLiteDatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void testDatabaseCreation() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void testCoffeeInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertCoffee("Coffee 2", "Description 2", 1.99);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("coffee", null, "name=?", new String[]{ "Coffee 2" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Coffee 2", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description 2", cursor.getString(cursor.getColumnIndex("description")));
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
        long id = dbHelper.insertCoffee("Deletion Coffee", "Desc", 4.99);

        // Verify tuple was added to database
        assertNotEquals(-1, id);

        // Remove the test tuple
        int rowsDeleted = dbHelper.deleteCoffee("Deletion Coffee");

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        Cursor cursor = db.query("coffee", null, "name=?", new String[]{ "Deletion Coffee" }, null, null, null);
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
        long id = dbHelper.insertTopping("Topping 2", "Description 2", 0.59);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("toppings", null, "name=?", new String[]{ "Topping 2" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Topping 2", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description 2", cursor.getString(cursor.getColumnIndex("description")));
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
        long id = dbHelper.insertTopping("Deletion Topping", "Desc", 0.42);

        // Verify tuple was added to database
        assertNotEquals(-1, id);

        // Remove the test tuple
        int rowsDeleted = dbHelper.deleteTopping("Deletion Topping");

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        Cursor cursor = db.query("toppings", null, "name=?", new String[]{ "Deletion Topping" }, null, null, null);
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
        long id = dbHelper.insertFlavor("Flavor 2", "Description 2", 0.99);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("flavors", null, "name=?", new String[]{ "Flavor 2" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Flavor 2", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description 2", cursor.getString(cursor.getColumnIndex("description")));
        assertEquals(0.99, cursor.getDouble(cursor.getColumnIndex("price")), 0.01);

        cursor.close();
        db.close();
    }

    @Test
    public void testFlavorRemoval() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to database
        long id = dbHelper.insertCoffee("Deletion Flavor", "Desc", 0.99);

        // Verify tuple was added to database
        assertNotEquals(-1, id);

        // Remove the test tuple
        int rowsDeleted = dbHelper.deleteCoffee("Deletion Flavor");

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        Cursor cursor = db.query("flavors", null, "name=?", new String[]{ "Deletion Flavor" }, null, null, null);
        assertFalse(cursor.moveToFirst());
        cursor.close();

        db.close();
    }

    @Test
    public void testUserInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertUser("password1", "email1@gmail.com", "FirstName1", "LastName1");

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "password1" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("password1", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("email1@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("FirstName1", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("LastName1", cursor.getString(cursor.getColumnIndex("lastName")));

        cursor.close();
        db.close();
    }

    @Test
    public void testUserLoginPositive() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertUser("password2", "email2@gmail.com", "FirstName2", "LastName2");

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "password2" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("password2", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("email2@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("FirstName2", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("LastName2", cursor.getString(cursor.getColumnIndex("lastName")));
        cursor.close();

        // Remove the test tuple
        boolean loggedIn = dbHelper.userLogin("email2@gmail.com", "password2");

        // Check if user was successfully logged in
        assertTrue(loggedIn);

        db.close();
    }

    @Test
    public void testUserLoginNegative() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteDatabaseHelper dbHelper = SQLiteDatabaseHelper.getInstance(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        long id = dbHelper.insertUser( "password3", "email3@gmail.com", "FirstName3", "LastName3");

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("users", null, "password=?", new String[]{ "password3" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("password3", cursor.getString(cursor.getColumnIndex("password")));
        assertEquals("email3@gmail.com", cursor.getString(cursor.getColumnIndex("email")));
        assertEquals("FirstName3", cursor.getString(cursor.getColumnIndex("firstName")));
        assertEquals("LastName3", cursor.getString(cursor.getColumnIndex("lastName")));
        cursor.close();

        // Remove the test tuple
        boolean loggedIn = dbHelper.userLogin("email3@gmail.com", "password2");

        // Check if user was successfully logged in
        assertFalse(loggedIn);

        db.close();
    }
}