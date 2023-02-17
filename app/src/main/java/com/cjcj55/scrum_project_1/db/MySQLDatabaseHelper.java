package com.cjcj55.scrum_project_1.db;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cjcj55.scrum_project_1.MainActivity;
import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;
import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabaseHelper {
    public static List<CoffeeItemInCatalog> getAllActiveCoffeeTypes(Context context) {
        List<CoffeeItemInCatalog> coffees = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
            "http://" + MainActivity.LOCAL_IP + "/coffeeorderingappserver/getAllActiveCoffeeTypes.php",
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
                "http://" + MainActivity.LOCAL_IP + "/coffeeorderingappserver/getAllActiveFlavorTypes.php",
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
                "http://" + MainActivity.LOCAL_IP + "/coffeeorderingappserver/getAllActiveToppingTypes.php",
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
