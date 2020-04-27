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

public class CompetitionsAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> competitionsList;

    public CompetitionsAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, 0, objects);

        mContext = context;
        competitionsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_competitions,parent,false);

        TextView textView1 = listItem.findViewById(R.id.textViewCompetitionName);
        textView1.setText(competitionsList.get(position));

        return listItem;
    }
}