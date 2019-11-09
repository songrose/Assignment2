package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MonthlyActivity extends AppCompatActivity  {
    TextView f;
    TextView t;
    TextView k;
    TextView month;
    EditText user;
    ArrayList<BloodReading> l = new ArrayList<>();
    ArrayList<BloodReading> b = new ArrayList<>();
    Button btn;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);


        backBtn = findViewById(R.id.backBtn);
        month = findViewById(R.id.monthyDate);
        btn = findViewById(R.id.buttonGet);
        user = findViewById(R.id.userss);
        f = findViewById(R.id.textSystolic);
        t = findViewById(R.id.textDialostic);
        k = findViewById(R.id.averageCondition);

        l = (ArrayList<BloodReading>)getIntent().getSerializableExtra("peoples");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean f  = false;
                String userName = user.getText().toString();
                for(BloodReading b : l) {
                    if(b.getUsername().equalsIgnoreCase(userName)){
                        f =true;

                    }
                }
                if(f) {
                    up(userName);
                } else {
                    Toast.makeText(getApplicationContext(),"Please enter a valid username",Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MonthlyActivity.super.onBackPressed();
            }
        });



    }

    @SuppressLint("SetTextI18n")
    private void up(String name) {
        b.clear();
        for(BloodReading c:l) {
            if(c.getUsername().equalsIgnoreCase(name)){
                b.add(c);
            }
        }
        String monthSys = "Monthly Systolic: ";
        String monthDia = "Monthly Dialostic: ";
        String monthlyCondition = "Monthly Conidition: ";
        String date = "Year and Month to Date: ";

        ArrayList<BloodReading> g = new ArrayList<>();
        g = BloodReading.getMonthly(b);
        month.setText(date+ g.get(0).getDate_time());
        f.setText(monthSys + g.get(0).getSystolic());

        t.setText(monthDia + g.get(0).getDialostic());

        k.setText(monthlyCondition + g.get(0).getCondition());


    }
}
