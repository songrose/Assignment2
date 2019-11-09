package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;

    EditText editTextSystolic;
    EditText editTextDialostic;
    Button buttonAddBloodReading;
    Button monthlyBtn;

    DatabaseReference databaseBloodReading;
    ListView lvBloodReading;
    ArrayList<BloodReading> bloodReadingList;
    ArrayList<BloodReading> l;
    ArrayList<BloodReading> b;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println( " Dfad" + Date.getDateAndTime());

        lvBloodReading = findViewById(R.id.lvBloodReading);
        bloodReadingList = new ArrayList<BloodReading>();
        l = new ArrayList<BloodReading>();
        b = new ArrayList<>();
        databaseBloodReading = FirebaseDatabase.getInstance().getReference("BloodReading");
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextSystolic = findViewById(R.id.editTextSystolic);
        editTextDialostic = findViewById(R.id.editTextDialostic);
        buttonAddBloodReading = findViewById(R.id.buttonAddBloodreading);
        monthlyBtn =  findViewById(R.id.buttonMonth);

        monthlyBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MonthlyActivity.class);
                i.putExtra("peoples",l);
                startActivity(i);
            }
        } );

        buttonAddBloodReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBloodReading();
            }
        });

        lvBloodReading.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("64");
                BloodReading bloodReading = bloodReadingList.get(position);

                showUpdateDialog(bloodReading.getUsername(), bloodReading.getBloodReadingID(),
                        bloodReading.getSystolic(),
                        bloodReading.getDialostic(),
                        bloodReading.getDate_time());

                return false;
            }
        });



    }
    private void addBloodReading() {
        String usernameString = editTextUsername.getText().toString().trim();
        String systolicString = editTextSystolic.getText().toString().trim();
        String dialosticString = editTextDialostic.getText().toString().trim();

        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "You must enter username.", Toast.LENGTH_LONG).show();
            return;
        }


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

        BloodReading br = new BloodReading(usernameString, id, systolic, dialostic,  Date.getDateAndTime());
        l.add(br);

        Task setValueTask = databaseBloodReading.child(id).setValue(br);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,"Blood Reading added.",Toast.LENGTH_LONG).show();
                editTextUsername.setText("");
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

    @Override
    protected void onStart() {
        super.onStart();
        databaseBloodReading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodReadingList.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                     BloodReading student = studentSnapshot.getValue(BloodReading.class);
                    bloodReadingList.add(student);
                    l.add(student);
                    System.out.println(l.size());
                }


                BloodReadingAdapter adapter = new BloodReadingAdapter(MainActivity.this, bloodReadingList);
                lvBloodReading.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });



    }
    private void updateStudent(String username, String id, int systolic, int dialostic, String date ) {
        DatabaseReference dbRef = databaseBloodReading.child(id);
    System.out.println("????149");
        BloodReading bloodReading = new BloodReading(username, id,systolic,dialostic, date);

        Task setValueTask = dbRef.setValue(bloodReading);


        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Blood Updated.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(final String username, final String id, final int systolic, final int dialostic, final String date) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        System.out.println("175" + username);
        LayoutInflater inflater = getLayoutInflater();



        final View dialogView = inflater.inflate(R.layout.update_dialogue, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextUsername = dialogView.findViewById(R.id.editTextUsername);

        editTextUsername.setText(username);

        final EditText editTextSystolic = dialogView.findViewById(R.id.editTextSystolic);
        editTextSystolic.setText(String.valueOf(systolic));

        final EditText editTextDialostic = dialogView.findViewById(R.id.editTextDialostic);
        editTextDialostic.setText(String.valueOf(dialostic));


        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update Blood Reading Systolic:" + systolic + " Dialostic: " + dialostic);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = editTextUsername.getText().toString().trim();
                String sysString = editTextSystolic.getText().toString().trim();
                String diaString = editTextDialostic.getText().toString().trim();

                if (TextUtils.isEmpty(sysString)) {
                    editTextSystolic.setError("Systolic is required");
                    return;
                } else if (TextUtils.isEmpty(diaString)) {
                    editTextDialostic.setError("Dialostic is required");
                    return;
                } else if (TextUtils.isEmpty(usernameString)) {
                    editTextDialostic.setError("username is required");
                    return;
                }
                int systolic2 = Integer.parseInt(sysString);
                int dialostic2 = Integer.parseInt(diaString);


                updateStudent(usernameString, id, systolic2, dialostic2, date);
                for(BloodReading a: l) {
                    if(a.getBloodReadingID().equalsIgnoreCase(id)) {
                        a.setSystolic(systolic2);
                        a.setDialostic(dialostic2);
                    }
                }



                alertDialog.dismiss();
            }
        });

        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBloodReading(id);

                alertDialog.dismiss();
            }
        });


    }


    private void deleteBloodReading(String id) {
        DatabaseReference dbRef = databaseBloodReading.child(id);
        Iterator<BloodReading> i = l.iterator();
        while(i.hasNext()){
            BloodReading b = i.next();
            if(b.getBloodReadingID().equalsIgnoreCase(id)){
                i.remove();
            }
        }



        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Blood Reading Deleted.",Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
