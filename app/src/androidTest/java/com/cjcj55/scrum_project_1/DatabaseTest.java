package com.cjcj55.scrum_project_1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cjcj55.scrum_project_1.db.DatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void testDatabaseCreation() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = new DatabaseHelper(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
    }

    @Test
    public void testDataInsertion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = new DatabaseHelper(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to the database
        ContentValues values = new ContentValues();
        values.put("name", "Coffee 1");
        values.put("description", "Description 1");
        values.put("price", "1.99");

        // Insert tuple into database
        long id = db.insert("coffee", null, values);

        // Check if insertion was successful
        assertNotEquals(-1, id);

        // Query the database to verify data was inserted
        Cursor cursor = db.query("coffee", null, "name=?", new String[]{ "Coffee 1" }, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Coffee 1", cursor.getString(cursor.getColumnIndex("name")));
        assertEquals("Description 1", cursor.getString(cursor.getColumnIndex("description")));
        assertEquals(1.99, cursor.getDouble(cursor.getColumnIndex("price")), 0.01);

        cursor.close();
    }

    @Test
    public void testDataRemoval() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper dbHelper = new DatabaseHelper(appContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Add a test tuple to database
        ContentValues values = new ContentValues();
        values.put("name", "Test Coffee");
        values.put("description", "A test coffee");
        values.put("price", "1.99");
        long id = db.insert("coffee", null, values);

        // Verify tuple was added to database
        Cursor cursor = db.query("coffee", null, null, null, null, null, null);
        assertNotEquals(-1, id);
        cursor.close();

        // Remove the test tuple
        int rowsDeleted = db.delete("coffee", "name=?", new String[]{ "Test Coffee" });

        // Check if removal was successful
        assertEquals(1, rowsDeleted);

        // Query the database to verify data was removed
        cursor = db.query("coffee", null, "name=?", new String[]{ "Test Coffee" }, null, null, null);
        assertFalse(cursor.moveToFirst());

        cursor.close();
    }
}