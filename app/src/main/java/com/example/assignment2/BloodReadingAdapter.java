package com.example.assignment2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BloodReadingAdapter extends ArrayAdapter<BloodReading> {
    private Activity context;
    private List<BloodReading> bloodReadingList;

    public BloodReadingAdapter(Activity context, List<BloodReading> bloodReadingList) {
        super(context, R.layout.list_layout, bloodReadingList);
        this.context = context;
        this.bloodReadingList = bloodReadingList;
    }

    public BloodReadingAdapter(Context context, int resource, List<BloodReading> objects, Activity context1, List<BloodReading> bloodReadingList) {
        super(context, resource, objects);
        this.context = context1;
        this.bloodReadingList = bloodReadingList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView tvUsername = listViewItem.findViewById(R.id.textViewUsername);

        TextView tvSystolic = listViewItem.findViewById(R.id.textViewSystolic);
        TextView tvDialostic = listViewItem.findViewById(R.id.textViewDialostic);
        TextView tvTime = listViewItem.findViewById(R.id.textViewTime);
        TextView tvCondition = listViewItem.findViewById(R.id.textViewCondition);
        BloodReading bloodReading = bloodReadingList.get(position);
        tvUsername.setText(bloodReading.getUsername());
        tvSystolic.setText(String.valueOf(bloodReading.getSystolic()));
        tvDialostic.setText(String.valueOf(bloodReading.getDialostic()));
        tvTime.setText(bloodReading.getDate_time());
        tvCondition.setText(bloodReading.getCondition());



        return listViewItem;
    }

}
