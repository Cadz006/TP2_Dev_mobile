package bdeb.qc.ca.tp2_dev_mobile.Database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseAPI extends AppCompatActivity {
    private String urlDB = "https://api2.defiphotos.tk/api/";

    private String  access_token = "";
    private String  token_type = "";

    private SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

    private void deconnecter() {
        if (access_token.isEmpty()) {
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://testing.defiphotos.tk/api/auth/deconnexion",
                null,
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        Log.i("onResponse", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void faireLaConnexionALaBD(String email, String password) {
        deconnecter();

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("email", email);
            parameters.put("mot_de_passe", password);
            parameters.put("remember_me", false);
        } catch (Exception e) {
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://testing.defiphotos.tk/api/auth/connexion",
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        Log.i("onResponse", response.toString());
                        access_token = findString(response, "access_token");
                        token_type = findString(response, "token_type");
                        String role = findString(response, "roles");
                        Context context = getApplicationContext();
                        obtenirInfo("https://testing.defiphotos.tk/api/sections", "sections");
                        obtenirInfo("https://testing.defiphotos.tk/api/questions-defaut", "questions-defaut");
                        obtenirInfo("https://testing.defiphotos.tk/api/questions-personalisees", "questions-personalisees");
                        obtenirInfo("https://testing.defiphotos.tk/api/questions-groupe", "questions-groupe");
                        obtenirInfo("https://testing.defiphotos.tk/api/annees-scolaire", "annees-scolaire");
                        obtenirInfo("https://testing.defiphotos.tk/api/commentaires", "commentaires");
                        obtenirInfo("https://testing.defiphotos.tk/api/stages", "stages");
                        obtenirInfo("https://testing.defiphotos.tk/api/comptes", "comptes");
                    }
                },
                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    public static String findString(JSONObject jObj, String findKey) {
        String finalValue = "";
        try {
            finalValue = findValue(jObj, findKey);
        } catch(JSONException e) {

        }
        return finalValue;
    }

    public static String findValue(JSONObject jObj, String findKey) throws JSONException {
        String finalValue = "";
        if (jObj == null) {
            return finalValue;
        }

        if (findKey.isEmpty()) {
            return finalValue;
        }

        Object obj = jObj.get(findKey);
        if (obj != null) {
            finalValue = obj.toString();
        }
        return finalValue;
    }

    private void obtenirInfo(String url, final String nom) {
        if (access_token.isEmpty()) {
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response) {
                        Log.i("onResponse", response.toString());
                        JSONArray array = null;
                        try {
                            array = response.getJSONArray("data");
                        } catch (JSONException e) {

                        }
                        if (array != null) {
                            parseJSONArray(array, nom);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());
                    }
                })
        {
            @Override public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token_type + " " + access_token);
                return headers;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void parseJSONArray(JSONArray array, String nom) {
        if (array == null || array.length() == 0) {
            return;
        }
        for (int i = 0; i < array.length(); i++) {
            JSONObject  obj = null;
            try {
                obj = array.getJSONObject(i);
            } catch(Exception e)
            {

            }
            enumerateObjet(obj, nom);
        }
    }

    public static void enumerateObjet(JSONObject jsonObject, String nom) {
        if (jsonObject == null) {
            return;
        }

        //SharedPreferences.Editor editor = sp.edit();

        Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) {
            String key = keys.next();
            Object obj = null;
            try {
                obj = jsonObject.get(key);
            } catch (JSONException e) {

            }
            if (obj != null) {
                if (obj instanceof JSONObject) {
                    enumerateObjet((JSONObject) obj, nom + "1");
                } else {
                    //Log.d("enumerateObjet", nom + "_____________" + key + " : " + obj.toString());
                    //editor.putString(key, obj.toString());
                }
            }
        }
    }

    public void getData(){

    }
}
