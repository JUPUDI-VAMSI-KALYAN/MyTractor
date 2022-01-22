package com.example.mytractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class DriverActivity extends AppCompatActivity {

    TextInputEditText driver_salary;
    Button submit;
    private FirebaseFirestore db;
    private RecyclerView mfirestorelist;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();

        driver_salary = findViewById(R.id.driver_salary_amount);
        submit = findViewById(R.id.diver_upload_data_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = driver_salary.getText().toString();
                if (TextUtils.isEmpty(amount)){
                    driver_salary.setError("Amount is Requried");
                    return;
                }
                Map<String, Object> salaryobj = new HashMap<>();
                salaryobj.put("amount",amount);
                salaryobj.put("timestamp", FieldValue.serverTimestamp());

                db.collection("driver").add(salaryobj)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "FaIled to add data contact vamsi", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }






}