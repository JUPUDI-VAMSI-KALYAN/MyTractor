package com.example.mytractor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    FirebaseFirestore db;
    TextInputEditText name,phone_number,hours,minutes,total_amount,paid_amount;
    RadioGroup radiogroup_paid_type;
    RadioButton radio_not_paid,radio_partially_paid,radio_fully_paid;
    Button upload_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();



        name = findViewById(R.id.cust_name);
        phone_number = findViewById(R.id.cust_phone);
        hours = findViewById(R.id.cust_hours);
        minutes = findViewById(R.id.cust_min);
        total_amount = findViewById(R.id.total_amount);
        paid_amount = findViewById(R.id.paid_amount);
        radiogroup_paid_type = findViewById(R.id.add_radio_group);
        upload_btn = findViewById(R.id.upload_data_btn);
        radio_not_paid = findViewById(R.id.radio_not_paid);
        radio_partially_paid =findViewById(R.id.radio_partially_paid);
        radio_fully_paid = findViewById(R.id.radio_fully_paid);


        //upload btn start
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cust_name = name.getText().toString().trim();
                String cust_phone = phone_number.getText().toString().trim();
                String cust_hours = hours.getText().toString().trim();
                String cust_minutes = minutes.getText().toString().trim();
                String cust_total_amount = total_amount.getText().toString().trim();
                String cust_paid_amount = paid_amount.getText().toString().trim();
                String not_paid="false",partially_paid ="false",fully_paid="false";

                if (TextUtils.isEmpty(cust_name)){
                    name.setError("Name is Requried");
                    return;
                }
                if (TextUtils.isEmpty(cust_phone)){
                    phone_number.setError("Phone Number is Requried");
                    return;
                }
                if (TextUtils.isEmpty(cust_hours)){
                    hours.setError("Hours is Requried");
                    return;
                }
                if (TextUtils.isEmpty(cust_minutes)){
                    minutes.setError("Minutes is Requried");
                    return;
                }
                if (TextUtils.isEmpty(cust_total_amount)){
                    total_amount.setError("Total amount is Requried");
                    return;
                }
                if (TextUtils.isEmpty(cust_paid_amount)){
                    paid_amount.setError("Paid Amount is Requried");
                    return;
                }
                if(radio_not_paid.isChecked()){
                    not_paid = "true";

                }
                if(radio_partially_paid.isChecked()){
                    partially_paid = "true";

                }
                if(radio_fully_paid.isChecked()){
                    fully_paid = "true";
                }

                //database start
                Map<String, Object> transaction = new HashMap<>();
                transaction.put("name", cust_name);
                transaction.put("phone", cust_phone);
                transaction.put("hours", cust_hours);
                transaction.put("minutes",cust_minutes);
                transaction.put("total_amount",cust_total_amount);
                transaction.put("paid_amount",cust_paid_amount);
                transaction.put("not_paid",not_paid);
                transaction.put("partially_paid",partially_paid);
                transaction.put("fully_paid",fully_paid);

                // Add a new document with a generated ID
                DocumentReference total_transactions = db.collection("info").document("tzEHGsk9HSxCk9jo9zp8");
                total_transactions.update("total_transactions", FieldValue.increment(1));
                total_transactions.update("tatal_amount",FieldValue.increment(Long.parseLong(cust_total_amount)));
                int hrs = (Integer.parseInt(cust_hours)*60)+Integer.parseInt(cust_minutes);
                total_transactions.update("total_hours",FieldValue.increment(hrs));

                Map<String, Object> user = new HashMap<>();
                user.put("name", cust_name);
                user.put("phone", cust_phone);
                db.collection("users").add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("AddActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(AddActivity.this,"User Added Successfully",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("AddActivity", "Error adding document", e);
                            }
                        });



                db.collection("transactions")
                        .add(transaction)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("AddActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Intent i = new Intent(AddActivity.this,DataAddedActivity.class);
                                startActivity(i);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("AddActivity", "Error adding document", e);
                            }
                        });

                //database end

            }
        });
        //upload btn end




    }
}