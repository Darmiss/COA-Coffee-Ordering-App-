package com.cjcj55.scrum_project_1.db;

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
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLDatabaseHelper {
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
