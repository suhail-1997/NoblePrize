package com.example.myapplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Designed and Developed by Mohammad suhail ahmed on 26/02/2020
 */
public class ApiRepository {
    public MutableLiveData<JSONObject> getWinners(Context context)
    {
        final MutableLiveData<JSONObject> resposeObject = new MutableLiveData<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, EndPointUrls.API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject  jsonObject = new JSONObject();
                try {
                    jsonObject.put("status","success");
                    jsonObject.put("response",response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resposeObject.setValue(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject jsonObject = new JSONObject();
                if(error.toString().contains("NoConnectionError"))
                {
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("status","Server is down try again later");
                        jsonObject.put("response","null");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    String errorres = new String(error.networkResponse.data);
                    try {
                        jsonObject.put("status","Cannot able to get the data");
                        jsonObject.put("response",new JSONObject(errorres));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                resposeObject.setValue(jsonObject);

            }
        });
        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);
        return resposeObject;
    }

}
