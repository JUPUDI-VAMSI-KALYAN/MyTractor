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

public class EditDataActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextInputEditText name,phone_number,hours,minutes,total_amount,paid_amount;
    RadioGroup radiogroup_paid_type;
    RadioButton radio_not_paid,radio_partially_paid,radio_fully_paid;
    Button upload_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();



        name = findViewById(R.id.edit_cust_name);
        phone_number = findViewById(R.id.edit_cust_phone);
        hours = findViewById(R.id.edit_cust_hours);
        minutes = findViewById(R.id.edit_cust_min);
        total_amount = findViewById(R.id.edit_total_amount);
        paid_amount = findViewById(R.id.edit_paid_amount);
        radiogroup_paid_type = findViewById(R.id.edit_add_radio_group);
        upload_btn = findViewById(R.id.edit_upload_data_btn);
        radio_not_paid = findViewById(R.id.radio_not_paid);
        radio_partially_paid =findViewById(R.id.radio_partially_paid);
        radio_fully_paid = findViewById(R.id.radio_fully_paid);

        Intent data = getIntent();
        name.setText(data.getStringExtra("name"));
        phone_number.setText((data.getStringExtra("phone")));
        hours.setText(data.getStringExtra("hours"));
        minutes.setText(data.getStringExtra("minutes"));
        total_amount.setText(data.getStringExtra("total_amount"));
        paid_amount.setText(data.getStringExtra("paid_amount"));
        String id = data.getStringExtra("item_id");


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
                transaction.put("timestamp", FieldValue.serverTimestamp());


                db.collection("transactions").document(id).set(transaction)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent i = new Intent(EditDataActivity.this,DataAddedActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("EditDataActivity", "Error adding document", e);
                            }
                        });

                //database end

            }
        });
        //upload btn end





    }
}