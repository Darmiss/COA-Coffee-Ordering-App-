package com.cjcj55.scrum_project_1.db;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjcj55.scrum_project_1.AccountCreationScreen;
import com.cjcj55.scrum_project_1.MainActivity;
import com.cjcj55.scrum_project_1.R;
import com.cjcj55.scrum_project_1.objects.UserCart;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.CoffeeItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.FlavorItem;
import com.cjcj55.scrum_project_1.objects.catalog.order_items.ToppingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLDatabaseHelper {
    public static List<UserCart> getAllTransactionsForUser(int userId, Context context) {
        List<UserCart> transactions = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getAllTransactionsForUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int transaction_id = jsonObject.getInt("transaction_id");
                                String timeOrdered = jsonObject.getString("time_ordered");
                                double price = jsonObject.getDouble("price");

//                                System.out.println(transaction_id + ": " + timeOrdered + ", $" + price);

                                // Insert orders, toppings, and flavors of each coffee item
                                List<CoffeeItem> coffeeItems = getCoffeeItemsForTransaction(transaction_id, context);

                                System.out.println("coffeeItems size: " + coffeeItems.size());

                                UserCart userCart = new UserCart();
                                for (CoffeeItem coffeeItem : coffeeItems) {
                                    userCart.addCoffeeToCart(coffeeItem);
                                }
                                userCart.setTimeOrdered(timeOrdered);
                                userCart.setPrice(price);
                                userCart.setTransactionId(transaction_id);

                                transactions.add(userCart);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", Integer.toString(userId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        return transactions;
    }

    private static List<CoffeeItem> getCoffeeItemsForTransaction(int transactionId, Context context) {
        List<CoffeeItem> coffeeItems = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getCoffeeItemsForTransaction.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int coffee_id = jsonObject.getInt("coffee_id");
                                int beverage_count = jsonObject.getInt("beverage_count");
                                int order_coffee_id = jsonObject.getInt("order_coffee_id");

//                                System.out.println(coffee_id + ": " + beverage_count + ", " + order_coffee_id);

                                // Get toppings for this coffee item
                                List<ToppingItem> toppingItems = getToppingItemsForOrderCoffee(order_coffee_id, context);

                                // Get flavors for this coffee item
                                List<FlavorItem> flavorItems = getFlavorItemsForOrderCoffee(order_coffee_id, context);

                                int coffeeIndex = -1;
                                // Loop to find which coffee has id = coffeeId
                                for (int j = 0; j < MainActivity.coffeeItemInCatalogTypes.size(); j++) {
                                    if (MainActivity.coffeeItemInCatalogTypes.get(j).getId() == coffee_id) {
                                        coffeeIndex = j;
                                    }
                                }

                                CoffeeItem coffeeItem = new CoffeeItem(MainActivity.coffeeItemInCatalogTypes.get(coffeeIndex), beverage_count);
                                coffeeItem.setToppingItemList(toppingItems);
                                coffeeItem.setFlavorItemList(flavorItems);
                                coffeeItems.add(coffeeItem);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("transaction_id", Integer.toString(transactionId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        return coffeeItems;
    }

    private static List<ToppingItem> getToppingItemsForOrderCoffee(int orderCoffeeId, Context context) {
        List<ToppingItem> toppingItems = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getToppingItemsForOrderCoffee.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int topping_id = jsonObject.getInt("topping_id");

//                                System.out.println(topping_id);

                                int toppingIndex = -1;
                                // Loop to find which topping has id = toppingId
                                for (int j = 0; j < MainActivity.toppingItemInCatalogTypes.size(); j++) {
                                    if (MainActivity.toppingItemInCatalogTypes.get(j).getId() == topping_id) {
                                        toppingIndex = j;
                                    }
                                }
                                ToppingItem toppingItem = new ToppingItem(MainActivity.toppingItemInCatalogTypes.get(toppingIndex));
                                toppingItems.add(toppingItem);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_coffee_id", Integer.toString(orderCoffeeId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        return toppingItems;
    }

    private static List<FlavorItem> getFlavorItemsForOrderCoffee(int orderCoffeeId, Context context) {
        List<FlavorItem> flavorItems = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getFlavorItemsForOrderCoffee.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int flavor_id = jsonObject.getInt("flavor_id");

//                                System.out.println(flavor_id);

                                int flavorIndex = -1;
                                // Loop to find which topping has id = toppingId
                                for (int j = 0; j < MainActivity.toppingItemInCatalogTypes.size(); j++) {
                                    if (MainActivity.toppingItemInCatalogTypes.get(j).getId() == flavor_id) {
                                        flavorIndex = j;
                                    }
                                }
                                FlavorItem flavorItem = new FlavorItem(MainActivity.flavorItemInCatalogTypes.get(flavorIndex));
                                flavorItems.add(flavorItem);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_coffee_id", Integer.toString(orderCoffeeId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        return flavorItems;
    }

    public static void insertTransactionFromCart(int userId, UserCart userCart, String pickupTime, double totalPrice, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/insertTransactionFromCart.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                int transaction_id = jsonObject.getInt("transaction_id");
                                Toast.makeText(context, "Transaction successfully inserted!", Toast.LENGTH_LONG).show();

                                // Insert orders, toppings, and flavors for each coffee item
                                List<CoffeeItem> coffeeItemList = userCart.getUserCart();
                                for (CoffeeItem coffeeItem : coffeeItemList) {
                                    insertOrderCoffee(transaction_id, coffeeItem, context);
                                }
                            } else {
                                Toast.makeText(context, "Unable to insert transaction", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", Integer.toString(userId));
                params.put("pickup_time", pickupTime);
                params.put("total_price", Double.toString(totalPrice));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private static void insertOrderCoffee(int transactionId, CoffeeItem coffeeItem, Context context) {
        StringRequest orderCoffeeRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/insertOrderCoffee.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                int order_coffee_id = jsonObject.getInt("order_coffee_id");

                                // Insert toppings for this coffee item
                                List<ToppingItem> toppingItemList = coffeeItem.getToppingItemList();
                                for (ToppingItem topping : toppingItemList) {
                                    insertOrderTopping(order_coffee_id, topping, context);
                                }

                                // Insert flavors for this coffee item
                                List<FlavorItem> flavorItemList = coffeeItem.getFlavorItemList();
                                for (FlavorItem flavor : flavorItemList) {
                                    insertOrderFlavor(order_coffee_id, flavor, context);
                                }
                            } else {
                                Toast.makeText(context, "Unable to insert beverage to database", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("transaction_id", Integer.toString(transactionId));
                params.put("coffee_id", Integer.toString(coffeeItem.getId()));
                params.put("beverage_count", Integer.toString(coffeeItem.getAmount()));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(orderCoffeeRequest);
    }

    private static void insertOrderTopping(int order_coffee_id, ToppingItem toppingItem, Context context) {
        StringRequest orderCoffeeToppingRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/insertOrderTopping.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (!success.equals("1")) {
                                Toast.makeText(context, "Unable to insert beverage's topping to database", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_coffee_id", Integer.toString(order_coffee_id));
                params.put("topping_id", Integer.toString(toppingItem.getId()));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(orderCoffeeToppingRequest);
    }

    private static void insertOrderFlavor(int order_coffee_id, FlavorItem flavorItem, Context context) {
        StringRequest orderCoffeeFlavorRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/insertOrderFlavor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (!success.equals("1")) {
                                Toast.makeText(context, "Unable to insert beverage's flavor to database", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_coffee_id", Integer.toString(order_coffee_id));
                params.put("flavor_id", Integer.toString(flavorItem.getId()));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(orderCoffeeFlavorRequest);
    }

//    public static void insertTransactionFromCart(int userId, UserCart userCart, String pickupTime, double totalPrice, Context context) {
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                "http://" + MainActivity.LOCAL_IP + "/insertTransactionFromCart.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            if (success.equals("1")) {
//                                int transaction_id = jsonObject.getInt("transaction_id");
//
//                                // Each coffee in transaction
//                                List<CoffeeItem> coffeeItemList = userCart.getUserCart();
//                                for (CoffeeItem coffeeItem : coffeeItemList) {
//                                    StringRequest orderCoffeeRequest = new StringRequest(Request.Method.POST,
//                                            "http://" + MainActivity.LOCAL_IP + "/insertOrderCoffee.php",
//                                            new Response.Listener<String>() {
//                                                @Override
//                                                public void onResponse(String response) {
//                                                    try {
//                                                        JSONObject jsonObject = new JSONObject(response);
//                                                        String success = jsonObject.getString("success");
//                                                        if (success.equals("1")) {
//                                                            int order_coffee_id = jsonObject.getInt("order_coffee_id");
//
//                                                            // Toppings per coffee
//                                                            List<ToppingItem> toppingItemList = coffeeItem.getToppingItemList();
//                                                            if (toppingItemList.size() > 0) {
//                                                                for (ToppingItem topping : toppingItemList) {
//                                                                    StringRequest orderToppingRequest = new StringRequest(Request.Method.POST,
//                                                                            "http://" + MainActivity.LOCAL_IP + "/insertOrderTopping.php",
//                                                                            new Response.Listener<String>() {
//                                                                                @Override
//                                                                                public void onResponse(String response) {
//                                                                                    try {
//                                                                                        JSONObject jsonObject = new JSONObject(response);
//                                                                                        String success = jsonObject.getString("success");
//                                                                                        if (!success.equals("1")) {
//                                                                                            Toast.makeText(context, "Unable to insert order topping item", Toast.LENGTH_LONG).show();
//                                                                                        }
//                                                                                    } catch (JSONException e) {
//                                                                                        throw new RuntimeException(e);
//                                                                                    }
//                                                                                }
//                                                                            }, new Response.ErrorListener() {
//                                                                        @Override
//                                                                        public void onErrorResponse(VolleyError error) {
//                                                                            Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
//                                                                        }
//                                                                    })
//                                                                    {
//                                                                        @Nullable
//                                                                        @Override
//                                                                        protected Map<String, String> getParams() throws AuthFailureError {
//                                                                            Map<String, String> params = new HashMap<>();
//                                                                            params.put("order_coffee_id", Integer.toString(order_coffee_id));
//                                                                            params.put("topping_id", Integer.toString(topping.getId()));
//                                                                            return params;
//                                                                        }
//                                                                    };
//                                                                    queue.add(orderToppingRequest);
//                                                                }
//                                                            }
//
//                                                            // Flavors per coffee
//                                                            List<FlavorItem> flavorItemList = coffeeItem.getFlavorItemList();
//                                                            if (toppingItemList.size() > 0) {
//                                                                for (FlavorItem flavor : flavorItemList) {
//                                                                    StringRequest orderToppingRequest = new StringRequest(Request.Method.POST,
//                                                                            "http://" + MainActivity.LOCAL_IP + "/insertOrderFlavor.php",
//                                                                            new Response.Listener<String>() {
//                                                                                @Override
//                                                                                public void onResponse(String response) {
//                                                                                    try {
//                                                                                        JSONObject jsonObject = new JSONObject(response);
//                                                                                        String success = jsonObject.getString("success");
//                                                                                        if (!success.equals("1")) {
//                                                                                            Toast.makeText(context, "Unable to insert order flavor item", Toast.LENGTH_LONG).show();
//                                                                                        }
//                                                                                    } catch (JSONException e) {
//                                                                                        throw new RuntimeException(e);
//                                                                                    }
//                                                                                }
//                                                                            }, new Response.ErrorListener() {
//                                                                        @Override
//                                                                        public void onErrorResponse(VolleyError error) {
//                                                                            Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
//                                                                        }
//                                                                    })
//                                                                    {
//                                                                        @Nullable
//                                                                        @Override
//                                                                        protected Map<String, String> getParams() throws AuthFailureError {
//                                                                            Map<String, String> params = new HashMap<>();
//                                                                            params.put("order_coffee_id", Integer.toString(order_coffee_id));
//                                                                            params.put("flavor_id", Integer.toString(flavor.getId()));
//                                                                            return params;
//                                                                        }
//                                                                    };
//                                                                    queue.add(orderToppingRequest);
//                                                                }
//                                                            }
//                                                        } else {
//                                                            Toast.makeText(context, "Unable to insert order coffee item", Toast.LENGTH_LONG).show();
//                                                        }
//                                                    } catch (JSONException e) {
//                                                        throw new RuntimeException(e);
//                                                    }
//                                                }
//                                            }, new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
//                                        }
//                                    })
//                                    {
//                                        @Nullable
//                                        @Override
//                                        protected Map<String, String> getParams() throws AuthFailureError {
//                                            Map<String, String> params = new HashMap<>();
//                                            params.put("transaction_id", Integer.toString(transaction_id));
//                                            params.put("coffee_id", Integer.toString(coffeeItem.getId()));
//                                            params.put("beverage_count", Integer.toString(coffeeItem.getAmount()));
//                                            return params;
//                                        }
//                                    };
//
//                                    queue.add(orderCoffeeRequest);
//
//
//                                }
//
//                                Toast.makeText(context, "Transaction successfully inserted!", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(context, "Unable to insert transaction", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        })
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", Integer.toString(userId));
//                params.put("pickup_time", pickupTime);
//                params.put("price", Double.toString(totalPrice));
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }
//
//
//    public static List<CoffeeItemInCatalog> getAllActiveCoffeeTypes(Context context) {
//        List<CoffeeItemInCatalog> coffees = new ArrayList<>();
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
//            "http://" + MainActivity.LOCAL_IP + "/getAllActiveCoffeeTypes.php",
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray coffeeItemsArray = response.getJSONArray("data");
//                            for (int i = 0; i < coffeeItemsArray.length(); i++) {
//                                JSONObject coffeeItemObject = coffeeItemsArray.getJSONObject(i);
//                                int id = coffeeItemObject.getInt("coffee_id");
//                                String name = coffeeItemObject.getString("name");
//                                String description = coffeeItemObject.getString("description");
//                                double price = coffeeItemObject.getDouble("price");
//                                CoffeeItemInCatalog coffeeItemInCatalog = new CoffeeItemInCatalog(id, name, description, price);
//                                coffees.add(coffeeItemInCatalog);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        // Add the request to the request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(jsonObjectRequest);
//        return coffees;
//    }

    public static List<CoffeeItemInCatalog> getAllActiveCoffeeTypes(Context context) {
        List<CoffeeItemInCatalog> coffees = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getAllActiveCoffeeTypes.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray coffeeItemsArray = response.getJSONArray("data");
                            for (int i = 0; i < coffeeItemsArray.length(); i++) {
                                JSONObject coffeeItemObject = coffeeItemsArray.getJSONObject(i);
                                int id = coffeeItemObject.getInt("coffee_id");
                                String name = coffeeItemObject.getString("name");
                                String description = coffeeItemObject.getString("description");
                                double price = coffeeItemObject.getDouble("price");
                                CoffeeItemInCatalog coffeeItemInCatalog = new CoffeeItemInCatalog(id, name, description, price);
                                coffees.add(coffeeItemInCatalog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return coffees;
    }

    public static List<FlavorItemInCatalog> getAllActiveFlavorTypes(Context context) {
        List<FlavorItemInCatalog> flavors = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getAllActiveFlavorTypes.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray flavorItemsArray = response.getJSONArray("data");
                            for (int i = 0; i < flavorItemsArray.length(); i++) {
                                JSONObject flavorItemObject = flavorItemsArray.getJSONObject(i);
                                int id = flavorItemObject.getInt("flavor_id");
                                String name = flavorItemObject.getString("name");
                                String description = flavorItemObject.getString("description");
                                double price = flavorItemObject.getDouble("price");
                                FlavorItemInCatalog flavorItemInCatalog = new FlavorItemInCatalog(id, name, description, price);
                                flavors.add(flavorItemInCatalog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return flavors;
    }

    public static List<ToppingItemInCatalog> getAllActiveToppingTypes(Context context) {
        List<ToppingItemInCatalog> toppings = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/getAllActiveToppingTypes.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray toppingItemsArray = response.getJSONArray("data");
                            for (int i = 0; i < toppingItemsArray.length(); i++) {
                                JSONObject toppingItemObject = toppingItemsArray.getJSONObject(i);
                                int id = toppingItemObject.getInt("topping_id");
                                String name = toppingItemObject.getString("name");
                                String description = toppingItemObject.getString("description");
                                double price = toppingItemObject.getDouble("price");
                                ToppingItemInCatalog toppingItemInCatalog = new ToppingItemInCatalog(id, name, description, price);
                                toppings.add(toppingItemInCatalog);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return toppings;
    }

    public static void insertCoffee(String name, String description, double price, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/addCoffee.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, "Coffee successfully added to catalog!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to add coffee to catalog", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                params.put("price", Double.toString(price));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh coffee list in MainActivity
        MainActivity.coffeeItemInCatalogTypes = getAllActiveCoffeeTypes(context);
    }

    public static void insertFlavor(String name, String description, double price, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/addFlavor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, "Flavor successfully added to catalog!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to add flavor to catalog", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
            })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                params.put("price", Double.toString(price));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh flavor list in MainActivity
        MainActivity.flavorItemInCatalogTypes = getAllActiveFlavorTypes(context);
    }

    public static void insertTopping(String name, String description, double price, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/addTopping.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, "Topping successfully added to catalog!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to add topping to catalog", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
            })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                params.put("price", Double.toString(price));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh topping list in MainActivity
        MainActivity.toppingItemInCatalogTypes = getAllActiveToppingTypes(context);
    }

    public static void toggleDisableCoffee(String name, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/toggleDisableCoffee.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, name + " Coffee is no longer active", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to make " + name + " inactive", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh coffee list in MainActivity
        MainActivity.coffeeItemInCatalogTypes = getAllActiveCoffeeTypes(context);
    }

    public static void toggleDisableTopping(String name, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/toggleDisableTopping.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, name + " Topping is no longer active", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to make " + name + " inactive", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh topping list in MainActivity
        MainActivity.toppingItemInCatalogTypes = getAllActiveToppingTypes(context);
    }

    public static void toggleDisableFlavor(String name, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + MainActivity.LOCAL_IP + "/toggleDisableFlavor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(context, name + " Flavor is no longer active", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to make " + name + " inactive", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error:" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

        // Refresh flavor list in MainActivity
        MainActivity.flavorItemInCatalogTypes = getAllActiveFlavorTypes(context);
    }


    private void setSessionId(HttpURLConnection connection, String sessionId) {
        if (sessionId != null) {
            connection.setRequestProperty("Cookie", "sessionid=" + sessionId);
        }
    }

    private String getSessionId(HttpURLConnection connection) {
        String headerName;
        for (int i = 1; (headerName = connection.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equals("Set-Cookie")) {
                String cookie = connection.getHeaderField(i);
                return cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
            }
        }
        return null;
    }
}
