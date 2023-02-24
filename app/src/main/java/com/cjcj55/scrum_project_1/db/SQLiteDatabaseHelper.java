package com.cjcj55.scrum_project_1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cjcj55.scrum_project_1.MainActivity;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.CoffeeItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.ToppingItem;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coffee.db";
    private static final int DATABASE_VERSION = 6;
    private static SQLiteDatabaseHelper instance;

    /**
     * @param context the app the database is a part of
     *
     * Initialize database.
     */
    private SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param context the app the database is a part of
     * @return the instance of the database
     */
    public static synchronized SQLiteDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteDatabaseHelper(context);
        }
        return instance;
    }

    // Define Coffee table columns
    private static final String COFFEE_TABLE = "CREATE TABLE coffee (coffee_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL, isActive BOOLEAN NOT NULL DEFAULT 1)";
    private static final String TOPPINGS_TABLE = "CREATE TABLE toppings (topping_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL, isActive BOOLEAN NOT NULL DEFAULT 1)";
    private static final String FLAVORS_TABLE = "CREATE TABLE flavors (flavor_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT, price REAL NOT NULL, isActive BOOLEAN NOT NULL DEFAULT 1)";

    // Define user table columns
    private static final String USERS_TABLE = "CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT NOT NULL, email TEXT NOT NULL, firstName TEXT NOT NULL, lastName TEXT NOT NULL)";
    private static final String EMPLOYEES_TABLE = "CREATE TABLE employees (employee_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT NOT NULL, email TEXT NOT NULL, firstName TEXT NOT NULL, lastName TEXT NOT NULL)";

    // Define transaction table columns
        // NOTE:  Transactions table has all whole transactions.  Within, has a foreign key to refer to Order Coffee table.
            // Order Coffee table has each individual item a user purchases.
    private static final String TRANSACTIONS_TABLE = "CREATE TABLE transactions (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "time_ordered TEXT DEFAULT (strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')), " +
            "pickup_time TEXT NOT NULL, " +
            "price REAL NOT NULL, " +
            "inProgress BOOLEAN NOT NULL DEFAULT 0, " +
            "fulfilled BOOLEAN NOT NULL DEFAULT 0, " +
            "cancelled_by_customer BOOLEAN NOT NULL DEFAULT 0, " +
            "isFavorite BOOLEAN NOT NULL DEFAULT 0, " +
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
        insertInitialDataToUsers(db);
        db.execSQL(EMPLOYEES_TABLE);
        insertInitialDataToEmployees(db);

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
        values.put("description", "A concentrated coffee served in a small, strong shot");
        values.put("price", 3.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Latte");
        values.put("description", "A milk coffee with silky foam and a shot of espresso");
        values.put("price", 4.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Americano");
        values.put("description", "A watered-down espresso");
        values.put("price", 4.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Cappuccino");
        values.put("description", "A single espresso shot topped with equal parts steamed and frothed milk");
        values.put("price", 3.99);
        db.insert("coffee", null, values);
        values.clear();

        values.put("name", "Iced Coffee");
        values.put("description", "A cold coffee with chilled milk");
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
        values.put("description", "A light and fluffy cream");
        values.put("price", 0.50);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Cinnamon");
        values.put("description", "A spice");
        values.put("price", 0.10);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Sprinkles");
        values.put("description", "Small, colorful pieces of confectionery");
        values.put("price", 0.15);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Marshmallows");
        values.put("description", "Squishy, fluffy, chewy sweetness");
        values.put("price", 0.50);
        db.insert("toppings", null, values);
        values.clear();

        values.put("name", "Ice Cream");
        values.put("description", "Cold vanilla dairy");
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
        values.put("description", "Rich, sweet, and sticky");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Mocha");
        values.put("description", "Chocolate that adds sweetness and velvety smoothness");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Hazelnut");
        values.put("description", "A nutty and creamy addition");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Vanilla");
        values.put("description", "Sweet, vanilla-y goodness");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Chocolate");
        values.put("description", "Chocolatey chocolate");
        values.put("price", 0.99);
        db.insert("flavors", null, values);
        values.clear();

        values.put("name", "Brown Sugar Cinnamon");
        values.put("description", "Cinnamon sugar cookie");
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

    public long fulfillTransaction(int transactionId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fulfilled", 1);
        String whereClause = "transaction_id=?";
        String[] whereArgs = {String.valueOf(transactionId)};
        long rowsUpdated = db.update("transactions", values, whereClause, whereArgs);
        return rowsUpdated;
    }

    public long toggleUserFavoriteOrder(int transactionId) {
//        System.out.println("User is toggling favorite for id:" + transactionId);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereClause = "transaction_id=?";
        String[] whereArgs = {String.valueOf(transactionId)};
        Cursor cursor = db.query("transactions", new String[]{"isFavorite"}, whereClause, whereArgs, null, null, null);
        if(cursor.moveToFirst()) {
            int isFavorite = cursor.getInt(cursor.getColumnIndex("isFavorite"));
            cursor.close();
            values.put("isFavorite", isFavorite == 1 ? 0 : 1);
            long rowsUpdated = db.update("transactions", values, whereClause, whereArgs);
            return rowsUpdated;
        } else {
            return -1;
        }
    }

    public long cancelUserOrder(int transactionId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cancelled_by_customer", 1);
        values.put("fulfilled",1);
        String whereClause = "transaction_id=?";
        String[] whereArgs = {String.valueOf(transactionId)};
        long rowsUpdated = db.update("transactions", values, whereClause, whereArgs);
        return rowsUpdated;
    }

//    public long setUserFavoriteOrder(int transactionId) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("isFavorite", 1);
//        String whereClause = "transaction_id=?";
//        String[] whereArgs = {String.valueOf(transactionId)};
//        long rowsUpdated = db.update("transactions", values, whereClause, whereArgs);
//        return rowsUpdated;
//    }
//
//    public long unsetUserFavoriteOrder(int transactionId) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("isFavorite", 0);
//        String whereClause = "transaction_id=?";
//        String[] whereArgs = {String.valueOf(transactionId)};
//        long rowsUpdated = db.update("transactions", values, whereClause, whereArgs);
//        return rowsUpdated;
//    }

    public List<UserCart> getAllUnfulfilledTransactions() {
        SQLiteDatabase db = getReadableDatabase();
        List<UserCart> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE fulfilled=? AND cancelled_by_customer=? ORDER BY pickup_time ASC";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(0), String.valueOf(0)});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex("transaction_id"));
                int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                String timeOrdered = cursor.getString(cursor.getColumnIndex("time_ordered"));
                String pickupTime = cursor.getString(cursor.getColumnIndex("pickup_time"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transactionId);

                UserCart userCart = new UserCart();
                for (CoffeeItem coffee : coffeeItems) {
                    userCart.addCoffeeToCart(coffee);
                }
                userCart.setTimeOrdered(timeOrdered);
                userCart.setPickupTime(pickupTime);
                userCart.setPrice(price);
                userCart.setUserId(userId);
                userCart.setTransactionId(transactionId);

                transactions.add(userCart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public List<UserCart> getAllUnfulfilledTransactionsForUser(int user_id) {
        SQLiteDatabase db = getReadableDatabase();
        List<UserCart> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE fulfilled=? AND user_id=? ORDER BY pickup_time ASC";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(0), Integer.toString(user_id)});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex("transaction_id"));
                String timeOrdered = cursor.getString(cursor.getColumnIndex("time_ordered"));
                String pickupTime = cursor.getString(cursor.getColumnIndex("pickup_time"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transactionId);

                UserCart userCart = new UserCart();
                for (CoffeeItem coffee : coffeeItems) {
                    userCart.addCoffeeToCart(coffee);
                }
                userCart.setTimeOrdered(timeOrdered);
                userCart.setPickupTime(pickupTime);
                userCart.setPrice(price);
                userCart.setUserId(user_id);
                userCart.setTransactionId(transactionId);

                transactions.add(userCart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public List<UserCart> getAllCompletedTransactions() {
        SQLiteDatabase db = getReadableDatabase();
        List<UserCart> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE fulfilled=? ORDER BY pickup_time DESC";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(1)});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex("transaction_id"));
                int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                String timeOrdered = cursor.getString(cursor.getColumnIndex("time_ordered"));
                String pickupTime = cursor.getString(cursor.getColumnIndex("pickup_time"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int cancelledByCustomer = cursor.getInt(cursor.getColumnIndex("cancelled_by_customer"));
                int fulfilled = cursor.getInt(cursor.getColumnIndex("fulfilled"));

                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transactionId);

                UserCart userCart = new UserCart();
                for (CoffeeItem coffee : coffeeItems) {
                    userCart.addCoffeeToCart(coffee);
                }
                userCart.setTimeOrdered(timeOrdered);
                userCart.setPickupTime(pickupTime);
                userCart.setPrice(price);
                userCart.setUserId(userId);
                userCart.setTransactionId(transactionId);
                userCart.setCancelledByCustomer(cancelledByCustomer);
                userCart.setFulfilled(fulfilled);

                transactions.add(userCart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public List<UserCart> getAllTransactionsForUser(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        List<UserCart> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE user_id=? ORDER BY time_ordered DESC";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex("transaction_id"));
                String timeOrdered = cursor.getString(cursor.getColumnIndex("time_ordered"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transactionId);

                UserCart userCart = new UserCart();
                for (CoffeeItem coffee : coffeeItems) {
                    userCart.addCoffeeToCart(coffee);
                }
                userCart.setTimeOrdered(timeOrdered);
                userCart.setPrice(price);
                userCart.setTransactionId(transactionId);

                transactions.add(userCart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public List<UserCart> getAllFavoriteTransactionsForUser(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        List<UserCart> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE user_id=? AND isFavorite=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userId), Integer.toString(1)});

        if (cursor.moveToFirst()) {
            do {
                int transactionId = cursor.getInt(cursor.getColumnIndex("transaction_id"));
                String timeOrdered = cursor.getString(cursor.getColumnIndex("time_ordered"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transactionId);

                UserCart userCart = new UserCart();
                for (CoffeeItem coffee : coffeeItems) {
                    userCart.addCoffeeToCart(coffee);
                }
                userCart.setTimeOrdered(timeOrdered);
                userCart.setPrice(price);

                transactions.add(userCart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    private List<CoffeeItem> getCoffeeItemsForTransaction(int transactionId) {
        SQLiteDatabase db = getReadableDatabase();
        List<CoffeeItem> coffeeItems = new ArrayList<>();

        String query = "SELECT * FROM order_coffee WHERE transaction_id=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(transactionId)});

        if (cursor.moveToFirst()) {
            do {
                int coffeeId = cursor.getInt(cursor.getColumnIndex("coffee_id"));
                int beverageCount = cursor.getInt(cursor.getColumnIndex("beverage_count"));
                int orderCoffeeId = cursor.getInt(cursor.getColumnIndex("order_coffee_id"));

                List<ToppingItem> toppingItems = getToppingItemsForOrderCoffee(orderCoffeeId);
                List<FlavorItem> flavorItems = getFlavorItemsForOrderCoffee(orderCoffeeId);

                int coffeeIndex = -1;
                // Loop to find which coffee has id = coffeeId
                for (int i = 0; i < MainActivity.coffeeItemInCatalogTypes.size(); i++) {
                    if (MainActivity.coffeeItemInCatalogTypes.get(i).getId() == coffeeId) {
                        coffeeIndex = i;
                    }
                }

                CoffeeItem coffeeItem = new CoffeeItem(MainActivity.coffeeItemInCatalogTypes.get(coffeeIndex), beverageCount);
                coffeeItem.setToppingItemList(toppingItems);
                coffeeItem.setFlavorItemList(flavorItems);
                coffeeItems.add(coffeeItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return coffeeItems;
    }

    private List<ToppingItem> getToppingItemsForOrderCoffee(int orderCoffeeId) {
        SQLiteDatabase db = getReadableDatabase();
        List<ToppingItem> toppingItems = new ArrayList<>();

        String query = "SELECT * FROM order_toppings_coffee WHERE order_coffee_id=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(orderCoffeeId)});

        if (cursor.moveToFirst()) {
            do {
                int toppingId = cursor.getInt(cursor.getColumnIndex("topping_id"));

                int toppingIndex = -1;
                // Loop to find which topping has id = toppingId
                for (int i = 0; i < MainActivity.toppingItemInCatalogTypes.size(); i++) {
                    if (MainActivity.toppingItemInCatalogTypes.get(i).getId() == toppingId) {
                        toppingIndex = i;
                    }
                }
                ToppingItem toppingItem = new ToppingItem(MainActivity.toppingItemInCatalogTypes.get(toppingIndex));
                toppingItems.add(toppingItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return toppingItems;
    }

    private List<FlavorItem> getFlavorItemsForOrderCoffee(int orderCoffeeId) {
        SQLiteDatabase db = getReadableDatabase();
        List<FlavorItem> flavorItems = new ArrayList<>();

        String query = "SELECT * FROM order_flavors_coffee WHERE order_coffee_id=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(orderCoffeeId)});

        if (cursor.moveToFirst()) {
            do {
                int flavorId = cursor.getInt(cursor.getColumnIndex("flavor_id"));

                int flavorIndex = -1;
                // Loop to find which flavor has id = flavorId
                for (int i = 0; i < MainActivity.flavorItemInCatalogTypes.size(); i++) {
                    if (MainActivity.flavorItemInCatalogTypes.get(i).getId() == flavorId) {
                        flavorIndex = i;
                    }
                }
                FlavorItem flavorItem = new FlavorItem(MainActivity.flavorItemInCatalogTypes.get(flavorIndex));
                flavorItems.add(flavorItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return flavorItems;
    }

    public long insertTransactionFromCart(int userId, UserCart userCart, String pickupTime, double totalPrice) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues transactionValues= new ContentValues();
        transactionValues.put("user_id", userId);
        transactionValues.put("pickup_time", pickupTime);
        transactionValues.put("price", totalPrice);
        long transactionId = db.insert("transactions", null, transactionValues);

        List<CoffeeItem> coffeeItemList = userCart.getUserCart();

        for (CoffeeItem coffeeItem : coffeeItemList) {
            ContentValues orderCoffeeValues = new ContentValues();
            orderCoffeeValues.put("transaction_id", transactionId);
            orderCoffeeValues.put("coffee_id", coffeeItem.getId());
            orderCoffeeValues.put("beverage_count", coffeeItem.getAmount());
            long orderCoffeeId = db.insert("order_coffee", null, orderCoffeeValues);

            List<ToppingItem> toppingItemList = coffeeItem.getToppingItemList();
            if (toppingItemList.size() > 0) {
                for (ToppingItem topping : toppingItemList) {
                    ContentValues orderToppingValues = new ContentValues();
                    orderToppingValues.put("order_coffee_id", orderCoffeeId);
                    orderToppingValues.put("topping_id", topping.getId());
                    db.insert("order_toppings_coffee", null, orderToppingValues);
                }
            }

            List<FlavorItem> flavorItemList = coffeeItem.getFlavorItemList();
            if (flavorItemList.size() > 0) {
                for (FlavorItem flavor : flavorItemList) {
                    ContentValues orderFlavorValues = new ContentValues();
                    orderFlavorValues.put("order_coffee_id", orderCoffeeId);
                    orderFlavorValues.put("flavor_id", flavor.getId());
                    db.insert("order_flavors_coffee", null, orderFlavorValues);
                }
            }
        }
        return transactionId;
    }

    /**
     * @param password The password for a customer
     * @param email The email for a customer
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     *
     * For registration, adds a new user to the Users table.
     * Consists of username, password, first name, and last name.
     */
    public long insertUser(String password, String email, String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("username", username);
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
//    public boolean userLogin(String email, String password) {
//        SQLiteDatabase db = getWritableDatabase();
//        String[] columns = {"user_id", "firstName", "lastName"};
//        String selection = "email=? AND password=?";
//        String[] selectionArgs = {email, password};
//        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        if (count > 0) {
//            cursor.moveToFirst();
//            int userIdIndex = cursor.getColumnIndex("user_id");
//            MainActivity.user = cursor.getInt(userIdIndex);
////            System.out.println("User logged in.  User now " + MainActivity.user);
//            cursor.close();
//            return true;
//        }
//        cursor.close();
//        return false;
//    }

    /**
     * @param password The password for an employee
     * @param firstName The employee's first name
     * @param lastName The employee's last name
     *
     * For registration, adds a new employee to the Employees table.
     * Consists of username, password, first name, and last name.
     */
    public long insertEmployee(String password, String email, String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        long id = db.insert("employees", null, values);
//        db.close();
        return id;
    }

    /**
     * @param email
     * @param password
     * @return true or false if the employee successfully logged in
     */
    public boolean employeeLogin(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {"employee_id", "firstName", "lastName"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("employees", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
//        db.close();
        return count > 0;
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
     * @param coffee Coffee name
     * @param isActive 0=False, 1=True
     * @return
     */
    public int updateCoffeeActive(String coffee, int isActive) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isActive", isActive);
        String whereClause = "name=?";
        String[] whereArgs = {coffee};
        int rowsUpdated = db.update("coffee", values, whereClause, whereArgs);
        return rowsUpdated;
    }

    /**
     * @param topping Topping name
     * @param isActive 0=False, 1=True
     * @return
     */
    public int updateToppingActive(String topping, int isActive) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isActive", isActive);
        String whereClause = "name=?";
        String[] whereArgs = {topping};
        int rowsUpdated = db.update("toppings", values, whereClause, whereArgs);
        return rowsUpdated;
    }

    public String getUserFirstName(int userId) {
        String firstName = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("users", new String[] {"firstName"}, "user_id=?", new String[] {String.valueOf(userId)}, null, null, null);
        if (cursor.moveToFirst()) {
            firstName = cursor.getString(0);
        }
        cursor.close();
        return firstName;
    }

    /**
     * @param flavor Flavor name
     * @param isActive 0=False, 1=True
     * @return
     */
    public int updateFlavorActive(String flavor, int isActive) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isActive", isActive);
        String whereClause = "name=?";
        String[] whereArgs = {flavor};
        int rowsUpdated = db.update("flavors", values, whereClause, whereArgs);
        return rowsUpdated;
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

    public static List<CoffeeItemInCatalog> getAllActiveCoffeeTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<CoffeeItemInCatalog> coffeeItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM coffee WHERE isActive IS NOT 0";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                CoffeeItemInCatalog coffeeItemInCatalog = new CoffeeItemInCatalog(id, name, description, price);
                coffeeItemInCatalogTypes.add(coffeeItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return coffeeItemInCatalogTypes;
    }

    public static List<CoffeeItemInCatalog> getAllCoffeeTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<CoffeeItemInCatalog> coffeeItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM coffee";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                CoffeeItemInCatalog coffeeItemInCatalog = new CoffeeItemInCatalog(id, name, description, price);
                coffeeItemInCatalogTypes.add(coffeeItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return coffeeItemInCatalogTypes;
    }

    public static List<ToppingItemInCatalog> getAllActiveToppingTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<ToppingItemInCatalog> toppingItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM toppings WHERE isActive IS NOT 0";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                ToppingItemInCatalog toppingItemInCatalog = new ToppingItemInCatalog(id, name, description, price);
                toppingItemInCatalogTypes.add(toppingItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return toppingItemInCatalogTypes;
    }

    public static List<ToppingItemInCatalog> getAllToppingTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<ToppingItemInCatalog> toppingItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM toppings";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                ToppingItemInCatalog toppingItemInCatalog = new ToppingItemInCatalog(id, name, description, price);
                toppingItemInCatalogTypes.add(toppingItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return toppingItemInCatalogTypes;
    }

    public static List<FlavorItemInCatalog> getAllActiveFlavorTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<FlavorItemInCatalog> flavorItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM flavors WHERE isActive IS NOT 0";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                FlavorItemInCatalog flavorItemInCatalog = new FlavorItemInCatalog(id, name, description, price);
                flavorItemInCatalogTypes.add(flavorItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return flavorItemInCatalogTypes;
    }

    public static List<FlavorItemInCatalog> getAllFlavorTypes(SQLiteDatabaseHelper db) {
        SQLiteDatabase sqLiteDB = db.getReadableDatabase();
        List<FlavorItemInCatalog> flavorItemInCatalogTypes = new ArrayList<>();

        String selectQuery = "SELECT * FROM flavors";
        Cursor cursor = sqLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                FlavorItemInCatalog flavorItemInCatalog = new FlavorItemInCatalog(id, name, description, price);
                flavorItemInCatalogTypes.add(flavorItemInCatalog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return flavorItemInCatalogTypes;
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

    public String getUsersFullName(int userId) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT firstName, lastName FROM users WHERE user_id=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userId)});

        String firstName = "";
        String lastName = "";

        if (cursor.moveToFirst()) {
            firstName = cursor.getString(cursor.getColumnIndex("firstName"));
            lastName = cursor.getString(cursor.getColumnIndex("lastName"));
        }

        return firstName + " " + lastName;
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
