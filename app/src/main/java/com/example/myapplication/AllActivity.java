package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllActivity extends AppCompatActivity {
    private Spinner catSpinner,yearSpinner;
    private TextView filter;
    private RecyclerView recyclerView;
    private List<WinnerDetails> winnerDetails;
    private List<String> categories,year;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private ArrayAdapter categoryAdapter,yearAdapter;
    private String catergoryString,yearString;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout errorlayout;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        catSpinner = findViewById(R.id.catspinner);
        yearSpinner = findViewById(R.id.datespinner);
        filter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.mainrecycler);
        shimmerFrameLayout = findViewById(R.id.shimmer);
        errorlayout = findViewById(R.id.allerrorlayout);
        errorText = findViewById(R.id.allerrortext);
        errorlayout.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();

        winnerDetails = new ArrayList<>();

        categories = new ArrayList<>();
        year = new ArrayList<>();
        categories.add("Categories");
        year.add("year");
        categoryAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,categories);
        yearAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,year);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mainRecyclerAdapter = new MainRecyclerAdapter(winnerDetails,AllActivity.this);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(mainRecyclerAdapter);

        catSpinner.setAdapter(categoryAdapter);
        yearSpinner.setAdapter(yearAdapter);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catergoryString = (String) parent.getItemAtPosition(position);
                catSpinner.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                catergoryString = "";

            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearString = (String) parent.getItemAtPosition(position);
                yearSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearString = "";

            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<WinnerDetails> w = new ArrayList<>();
                if(catergoryString.equals("Categories") && yearString.equals("year"))
                {
                    w.addAll(winnerDetails);
                }
                else
                {
                    for(WinnerDetails details : winnerDetails)
                    {
                        if(catergoryString.equals("Categories") && !TextUtils.isEmpty(yearString))
                        {
                            if(details.getYear().contains(yearString))
                            {
                                w.add(details);
                            }
                        }
                        else if(yearString.equals("year") && !TextUtils.isEmpty(catergoryString))
                        {
                            if(details.getCategory().contains(catergoryString))
                            {
                                w.add(details);
                            }
                        }
                        else
                        {
                            if(details.getCategory().contains(catergoryString) && details.getYear().contains(yearString))
                            {
                                w.add(details);
                            }
                        }

                    }
                }
                mainRecyclerAdapter.setWinnerDetails(w);
            }
        });

        if(new CheckInternet().checkInternet(AllActivity.this))
        {
            new ApiRepository().getWinners(AllActivity.this).observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {

                    try {
                        if(jsonObject.getString("status").equals("success"))
                        {
                            JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("prizes");
                            List<WinnerDetails> wd = new ArrayList<>();

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                List<PersonDetails> pd = new ArrayList<>();
                                JSONArray persondetails = jsonArray.getJSONObject(i).optJSONArray("laureates");
                                if(!categories.contains(jsonArray.getJSONObject(i).getString("category"))) {
                                    categories.add(jsonArray.getJSONObject(i).getString("category"));
                                }
                                if(!year.contains(jsonArray.getJSONObject(i).getString("year"))) {
                                    year.add(jsonArray.getJSONObject(i).getString("year"));
                                }
                                if(persondetails != null)
                                {
                                    for(int j=0;j<persondetails.length();j++)
                                    {
                                        pd.add(new PersonDetails(persondetails.getJSONObject(j).optInt("id"),persondetails.getJSONObject(j).optString("firstname"),persondetails.getJSONObject(j).optString("surname"),persondetails.getJSONObject(j).optString("motivation")));

                                    }
                                    wd.add(new WinnerDetails(jsonArray.getJSONObject(i).getString("year"),jsonArray.getJSONObject(i).getString("category"),pd));
                                }

                            }
                            categoryAdapter.notifyDataSetChanged();
                            yearAdapter.notifyDataSetChanged();

                            winnerDetails = wd;

                            mainRecyclerAdapter.setWinnerDetails(wd);


                        }
                        else
                        {
                            errorlayout.setVisibility(View.VISIBLE);
                            errorText.setText(jsonObject.getString("status"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            errorlayout.setVisibility(View.VISIBLE);
            errorText.setText("No Internet");
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }
}
