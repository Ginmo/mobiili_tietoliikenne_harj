package com.example.mobiilitietoliikenne_h3_t2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LeagueAreasAdapter extends ArrayAdapter<LeagueAreas> {
    private Context mContext;
    private ArrayList<LeagueAreas> areasList;

    public LeagueAreasAdapter(@NonNull Context context, @NonNull ArrayList<LeagueAreas> objects) {
        super(context, 0, objects);

        mContext = context;
        areasList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_league_areas,parent,false);

        LeagueAreas currentArea = areasList.get(position);

        // AREA NAME
        TextView textView1 = listItem.findViewById(R.id.textViewAreaName);
        textView1.setText(currentArea.getName());

        return listItem;
    }
}
