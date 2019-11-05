package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editTextSystolic;
    EditText editTextDialostic;
    Button buttonAddBloodReading;

    DatabaseReference databaseBloodReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println( " Dfad" + Date.getDateAndTime());


        databaseBloodReading = FirebaseDatabase.getInstance().getReference("BloodReading");

        editTextSystolic = findViewById(R.id.editTextSystolic);
        editTextDialostic = findViewById(R.id.editTextDialostic);
        buttonAddBloodReading = findViewById(R.id.buttonAddBloodreading);

        buttonAddBloodReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBloodReading();
            }
        });




    }
    private void addBloodReading() {
        String systolicString = editTextSystolic.getText().toString().trim();
        String dialosticString = editTextDialostic.getText().toString().trim();




        if (TextUtils.isEmpty(systolicString)) {
            Toast.makeText(this, "You must enter Systolic Blood Reading.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(dialosticString)) {
            Toast.makeText(this, "You must enter Dialostic Blood Reading.", Toast.LENGTH_LONG).show();
            return;
        }
        int systolic = Integer.parseInt(systolicString);
        int dialostic = Integer.parseInt(dialosticString);
        String id = databaseBloodReading.push().getKey();

        BloodReading br = new BloodReading(id, systolic, dialostic,  Date.getDateAndTime());

        Task setValueTask = databaseBloodReading.child(id).setValue(br);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,"Blood Reading added.",Toast.LENGTH_LONG).show();

                editTextSystolic.setText("");
                editTextDialostic.setText("");
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



}
