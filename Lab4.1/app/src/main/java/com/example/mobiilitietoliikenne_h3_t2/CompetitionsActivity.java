package com.example.mobiilitietoliikenne_h3_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompetitionsActivity extends AppCompatActivity {

    private ListView listViewCompetitions;
    private RequestQueue requestQueue;
    private int AREA_ID;
    private Competitions competitions;
    private ArrayList<Competitions> competitionsList;
    private CompetitionsAdapter competitionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);
        AREA_ID = getIntent().getIntExtra("AREA_ID", 0);
        listViewCompetitions = findViewById(R.id.listViewCompetitions);
        competitionsList = new ArrayList<>();
        competitionsAdapter = new CompetitionsAdapter(this, competitionsList);

        requestQueue = Volley.newRequestQueue(this);
        getCompetitions("https://api.football-data.org/v2/competitions?areas=" + AREA_ID);

    }

    private void getCompetitions(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("competitions");
                            if (jsonArray.length() < 1) {
                                competitions = new Competitions(0, "Competitions not found.");
                                competitionsList.add(competitions);
                            }
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    int ID = jsonObject.getInt("id");
                                    competitions = new Competitions(ID, name);
                                    competitionsList.add(competitions);
                                }
                            }

                            createList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void createList() {
        listViewCompetitions.setAdapter(competitionsAdapter);
    }
}
