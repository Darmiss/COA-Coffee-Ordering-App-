package com.cjcj55.scrum_project_1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cjcj55.scrum_project_1.objects.Coffee;
import com.cjcj55.scrum_project_1.objects.Flavor;
import com.cjcj55.scrum_project_1.objects.Topping;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coffee.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    /**
     * @param context the app the database is a part of
     *
     * Initialize database.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param context the app the database is a part of
     * @return the instance of the database
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    // Define Coffee table columns
    private static final String COFFEE_TABLE = "CREATE TABLE coffee (coffee_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL)";
    private static final String TOPPINGS_TABLE = "CREATE TABLE toppings (topping_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL)";
    private static final String FLAVORS_TABLE = "CREATE TABLE flavors (flavor_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL)";

    // Define user table columns
    private static final String USERS_TABLE = "CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL, firstName TEXT NOT NULL, lastName TEXT NOT NULL)";
    private static final String EMPLOYEES_TABLE = "CREATE TABLE employees (employee_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL, firstName TEXT NOT NULL, lastName TEXT NOT NULL)";

    // Define transaction table columns
        // NOTE:  Transactions table has all whole transactions.  Within, has a foreign key to refer to Order Coffee table.
            // Order Coffee table has each individual item a user purchases.
    private static final String TRANSACTIONS_TABLE = "CREATE TABLE transactions (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "time_ordered DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "pickup_time DATETIME NOT NULL, " +
            "price REAL NOT NULL, " +
            "fulfilled BOOLEAN NOT NULL DEFAULT 0, " +
            "cancelled_by_customer BOOLEAN NOT NULL DEFAULT 0, " +
            "FOREIGN KEY (user_id) REFERENCES users(user_id))";
    private static final String ORDER_COFFEE = "CREATE TABLE order_coffee (order_coffee_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "transaction_id INTEGER NOT NULL, " +
            "coffee_id INTEGER NOT NULL," +
            "beverage_count INTEGER NOT NULL, " +
            "FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id), " +
            "FOREIGN KEY (coffee_id) REFERENCES coffee(coffee_id), " +
            "CHECK (beverage_count >= 0))";

    private static final String ORDER_TOPPINGS_COFFEE = "CREATE TABLE order_toppings_coffee (order_toppings_coffee_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "order_coffee_id INTEGER NOT NULL, " +
            "topping_id INTEGER NOT NULL, " +
            "FOREIGN KEY (order_coffee_id) REFERENCES order_coffee(order_coffee_id), " +
            "FOREIGN KEY (topping_id) REFERENCES toppings(topping_id))";
    private static final String ORDER_FLAVORS_COFFEE = "CREATE TABLE order_flavors_coffee (order_flavors_coffee_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "order_coffee_id INTEGER NOT NULL, " +
            "flavor_id INTEGER NOT NULL, " +
            "FOREIGN KEY (order_coffee_id) REFERENCES order_coffee(order_coffee_id), " +
            "FOREIGN KEY (flavor_id) REFERENCES flavors(flavor_id))";

    /**
     * @param db database to be initialized
     *
     * Initializes the database, creating all tables and populating those that require population.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COFFEE_TABLE);
        insertInitialDataToCoffee(db);
        db.execSQL(TOPPINGS_TABLE);
        insertInitialDataToToppings(db);
        db.execSQL(FLAVORS_TABLE);
        insertInitialDataToFlavors(db);

        db.execSQL(USERS_TABLE);
        db.execSQL(EMPLOYEES_TABLE);

        db.execSQL(TRANSACTIONS_TABLE);
        db.execSQL(ORDER_COFFEE);
        db.execSQL(ORDER_TOPPINGS_COFFEE);
        db.execSQL(ORDER_FLAVORS_COFFEE);
    }

    /**
     * @param db database to be updated
     * @param oldVersion version of database to be replaced
     * @param newVersion version of database to replace old version
     *
     * If database version is changed, update all tables.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades
        db.execSQL("DROP TABLE IF EXISTS coffee");
        db.execSQL("DROP TABLE IF EXISTS toppings");
        db.execSQL("DROP TABLE IF EXISTS flavors");

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS employees");

        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS order_coffee");
        db.execSQL("DROP TABLE IF EXISTS order_toppings_coffee");
        db.execSQL("DROP TABLE IF EXISTS order_flavors_coffee");

        onCreate(db);
    }

    /**
     * @param db
     *
     * Populates the empty Coffee table with tuples
     */
    private void insertInitialDataToCoffee(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "Espresso");
        values.put("description", "");
        values.put("price", 3.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Latte");
        values.put("description", "");
        values.put("price", 4.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Americano");
        values.put("description", "");
        values.put("price", 4.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Cappuccino");
        values.put("description", "");
        values.put("price", 3.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Iced Coffee");
        values.put("description", "");
        values.put("price", 4.99);
        db.insert("coffee", null, values);
        values.clear();
    }

    /**
     * @param db
     *
     * Populates the empty Toppings table with tuples
     */
    private void insertInitialDataToToppings(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "Whipped Cream");
        values.put("description", "");
        values.put("price", 0.50);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Cinnamon");
        values.put("description", "");
        values.put("price", 0.10);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Sprinkles");
        values.put("description", "");
        values.put("price", 0.15);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Marshmallows");
        values.put("description", "");
        values.put("price", 0.50);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Ice Cream");
        values.put("description", "");
        values.put("price", 1.00);
        db.insert("toppings", null, values);
        values.clear();
    }

    /**
     * @param db
     *
     * Populates empty Flavors table with tuples
     */
    private void insertInitialDataToFlavors(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "Caramel");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Mocha");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Hazelnut");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Vanilla");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Chocolate");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Brown Sugar Cinnamon");
        values.put("description", "");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();
    }

    /**
     * @param db
     *
     * Populates empty Users table with tuples
     */
    private void insertInitialDataToUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("username", "john");
        values.put("password", "coffee");
        values.put("email", "johnjones@mail.com");
        values.put("firstName", "John");
        values.put("lastName", "Jones");
        db.insert("users", null, values);
        values.clear();

        values = new ContentValues();
        values.put("username", "terry");
        values.put("password", "terrycruz123");
        values.put("email", "tcruz@gmail.com");
        values.put("firstName", "Terry");
        values.put("lastName", "Cruz");
        db.insert("users", null, values);
        values.clear();
    }

    /**
     * @param db
     *
     * Populates empty Employees table with tuples
     */
    private void insertInitialDataToEmployees(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("username", "cjcj55");
        values.put("password", "coffee123");
        values.put("email", "cmp189@pitt.edu");
        values.put("firstName", "Chris");
        values.put("lastName", "Perrone");
        db.insert("employees", null, values);
        values.clear();
    }

    /**
     * @param name The name of the coffee to be added
     * @param description The description of the coffee to be added
     * @param price The price of the coffee to be added
     *
     * Insert a Coffee tuple into the Coffee table
     */
    public long insertCoffee(String name, String description, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        long id = db.insert("coffee", null, values);
//        db.close();
        return id;
    }

    /**
     * @param name The name of a topping to be added
     * @param description the description of a topping to be added
     * @param price the price of a topping to be added
     *
     * Insert a Topping tuple into the Toppings table
     */
    public long insertTopping(String name, String description, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        long id = db.insert("toppings", null, values);
//        db.close();
        return id;
    }

    /**
     * @param name The name of a flavor to be added
     * @param description the description of a flavor to be added
     * @param price the price of a flavor to be added
     *
     * Insert a Flavor tuple into the Flavors table
     */
    public long insertFlavor(String name, String description, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        long id = db.insert("flavors", null, values);
//        db.close();
        return id;
    }

    /**
     * @param username The unique username for a customer
     * @param password The password for a customer
     * @param email The email for a customer
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     *
     * For registration, adds a new user to the Users table.
     * Consists of username, password, first name, and last name.
     */
    public long insertUser(String username, String password, String email, String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        long id = db.insert("users", null, values);
//        db.close();
        return id;
    }

    /**
     * @param email
     * @param password
     * @return true or false if the user successfully logged in
     */
    public boolean userLogin(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {"user_id", "username", "firstName", "lastName"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
//        db.close();
        return count > 0;
    }

    /**
     * @param username The employee's unique username
     * @param password The password for an employee
     * @param firstName The employee's first name
     * @param lastName The employee's last name
     *
     * For registration, adds a new employee to the Employees table.
     * Consists of username, password, first name, and last name.
     */
    public long insertEmployee(String username, String password, String email, String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        long id = db.insert("employees", null, values);
//        db.close();
        return id;
    }

    /**
     * @param coffeeId the coffee_id from the Coffee table in the database
     *
     * Removes a Coffee from the Coffee table, provided a valid id is given.
     */
    public int deleteCoffee(int coffeeId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "coffee_id=?";
        String[] whereArgs = { Integer.toString(coffeeId) };
        int rowDeleted = db.delete("coffee", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    /**
     * @param coffee the coffee name from the Coffee table in the database
     *
     * Removes a Coffee from the Coffee table, provided a valid coffee name is given.
     */
    public int deleteCoffee(String coffee) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = { coffee };
        int rowDeleted = db.delete("coffee", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    /**
     * @param toppingId the topping_id from the Toppings table in the database
     *
     * Removes a Topping from the Toppings table, provided a valid id is given.
     */
    public int deleteTopping(int toppingId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "topping_id=?";
        String[] whereArgs = { Integer.toString(toppingId) };
        int rowDeleted = db.delete("toppings", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    /**
     * @param topping the topping name from the Toppings table in the database
     *
     * Removes a Topping from the Toppings table, provided a valid topping name is given.
     */
    public int deleteTopping(String topping) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = { topping };
        int rowDeleted = db.delete("toppings", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    /**
     * @param flavorId the flavor_id from the Flavors table in the database
     *
     * Removes a Flavor from the Flavors table, provided a valid id is given.
     */
    public int deleteFlavor(int flavorId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "flavor_id=?";
        String[] whereArgs = { Integer.toString(flavorId) };
        int rowDeleted = db.delete("flavors", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    /**
     * @param flavor the flavor name from the Flavors table in the database
     *
     * Removes a Flavor from the Flavors table, provided a valid flavor name is given.
     */
    public int deleteFlavor(String flavor) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = { flavor };
        int rowDeleted = db.delete("flavors", whereClause, whereArgs);
//        db.close();
        return rowDeleted;
    }

    public static List<Coffee> getAllCoffeeTypes(DatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<Coffee> coffeeTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM coffee";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                Coffee coffee = new Coffee(id, name, description, price);
                coffeeTypes.add(coffee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return coffeeTypes;
    }

    public static List<Topping> getAllToppingTypes(DatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<Topping> toppingTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM toppings";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                Topping topping = new Topping(id, name, description, price);
                toppingTypes.add(topping);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return toppingTypes;
    }

    public static List<Flavor> getAllFlavorTypes(DatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<Flavor> flavorTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM flavors";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                Flavor flavor = new Flavor(id, name, description, price);
                flavorTypes.add(flavor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return flavorTypes;
    }

    /**
     * @return list of all users
     *
     * Returns list of all respective usernames, first names, and last names.
     * Passwords are not returned.
     */
    public Cursor getAllUsers() {
        String[] columns = { "username", "email", "firstName", "lastName" };
        SQLiteDatabase db = getReadableDatabase();
        return db.query("users", columns, null, null, null, null, null);
    }

    /**
     * @return list of all employees
     *
     * Returns list of all respective usernames, first names, and last names.
     * Passwords are not returned.
     */
    public Cursor getAllEmployees() {
        String[] columns = { "username", "email", "firstName", "lastName" };
        SQLiteDatabase db = getReadableDatabase();
        return db.query("employees", columns, null, null, null, null, null);
    }
}
