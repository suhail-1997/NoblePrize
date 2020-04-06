package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopActivity extends AppCompatActivity {
    private RecyclerView topRecycler;
    private TopPeopleAdapter topPeopleAdapter;
    private List<TopPeopleItem> topPeopleItems;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout errorlayout;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        topRecycler = findViewById(R.id.memberRecycler);
        shimmerFrameLayout = findViewById(R.id.topshimmer);
        errorlayout = findViewById(R.id.toperrorlayout);
        errorText = findViewById(R.id.toperrortext);
        shimmerFrameLayout.startShimmer();
        topPeopleItems = new ArrayList<>();
        errorlayout.setVisibility(View.GONE);

        topPeopleAdapter = new TopPeopleAdapter(topPeopleItems);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(TopActivity.this,2);
        topRecycler.setLayoutManager(gridLayoutManager);
        topRecycler.setAdapter(topPeopleAdapter);


        if(new CheckInternet().checkInternet(TopActivity.this))
        {
            new ApiRepository().getWinners(TopActivity.this).observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        if(jsonObject.getString("status").equals("success"))
                        {
                            HashMap<String,String> map = new HashMap<>();
                            JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("prizes");

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONArray persondetails = jsonArray.getJSONObject(i).optJSONArray("laureates");
                                if(persondetails != null)
                                {
                                    for(int j=0;j<persondetails.length();j++)
                                    {
                                        if(map.containsKey(persondetails.getJSONObject(j).optString("firstname")+" "+persondetails.getJSONObject(j).optString("surname")))
                                        {
                                            String year = map.get(persondetails.getJSONObject(j).optString("firstname")+" "+persondetails.getJSONObject(j).optString("surname"));
                                            map.put(persondetails.getJSONObject(j).optString("firstname")+" "+persondetails.getJSONObject(j).optString("surname"),year+","+jsonArray.getJSONObject(i).optString("year"));

                                        }
                                        else
                                        {
                                            if(!TextUtils.isEmpty(persondetails.getJSONObject(j).optString("surname")))
                                            map.put(persondetails.getJSONObject(j).optString("firstname")+" "+persondetails.getJSONObject(j).optString("surname"),jsonArray.getJSONObject(i).optString("year"));
                                        }
                                    }
                                }

                            }

                            for (String name : map.keySet())
                            {
                                String years = map.get(name);
                                if(years.split(",").length > 1)
                                {
                                    topPeopleItems.add(new TopPeopleItem(name,map.get(name)));
                                }

                            }
                            topPeopleAdapter.setTopPeopleItems(topPeopleItems);
                        }
                        else
                        {
                            errorlayout.setVisibility(View.VISIBLE);
                            errorText.setText(jsonObject.getString("status"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            errorlayout.setVisibility(View.VISIBLE);
            errorText.setText("No Internet Connection");
            shimmerFrameLayout.setVisibility(View.GONE);
        }

    }
}
