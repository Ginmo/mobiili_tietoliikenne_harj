package com.example.mobiilitietoliikenne_h3_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ListView listViewAreas;
    private ArrayList<LeagueAreas> leaguesList;
    private LeagueAreas leagueAreas;
    private LeagueAreasAdapter leagueAreasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        listViewAreas = findViewById(R.id.listViewAreas);
        leaguesList = new ArrayList<>();
        leagueAreasAdapter = new LeagueAreasAdapter(this, leaguesList);

        getAreas("https://api.football-data.org/v2/areas");
    }

    private void getAreas(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("areas");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                int ID = jsonObject.getInt("id");
                                leagueAreas = new LeagueAreas(ID, name);
                                leaguesList.add(leagueAreas);
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
        listViewAreas.setAdapter(leagueAreasAdapter);

        listViewAreas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CompetitionsActivity.class);
                intent.putExtra("AREA_ID", leaguesList.get(i).getID());
                startActivity(intent);
            }
        });
    }
}
