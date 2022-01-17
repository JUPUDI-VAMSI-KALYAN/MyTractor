package com.example.mytractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    LinearLayout add,pending,done,conacts;
    FirebaseFirestore db;
    TextView home_hours,home_trans;
    private RecyclerView home_mfirestorelist;
    private FirebaseFirestore home_firebaseFirestore;
    private FirestoreRecyclerAdapter home_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        db = FirebaseFirestore.getInstance();
        home_hours = findViewById(R.id.home_hours);
        home_trans = findViewById(R.id.home_trans);

        home_mfirestorelist = findViewById(R.id.recycleview_home_pending);
        home_firebaseFirestore = FirebaseFirestore.getInstance();
        //update home
        db.collection("info").document("tzEHGsk9HSxCk9jo9zp8").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("HomeActivity","Success");
                        home_hours.setText(String.valueOf(Math.floor(documentSnapshot.getDouble("total_hours")/60)));
                        home_trans.setText(String.valueOf(documentSnapshot.getDouble("total_transactions").intValue()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("HomeActivity","ON Failure",e);
            }
        });

        //update home end





        add = findViewById(R.id.add_btn);
        pending = findViewById(R.id.pending_btn);
        done = findViewById(R.id.done_btn);
        conacts = findViewById(R.id.contact_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(HomeActivity.this,AddActivity.class);
                startActivity(i);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,PendingActivity.class);
                startActivity(i);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,DoneActivity.class);
                startActivity(i);
            }
        });
        conacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,ContactActivity.class);
                startActivity(i);
            }
        });


        Query query = home_firebaseFirestore.collection("transactions").whereEqualTo("fully_paid","false").limit(20);
        FirestoreRecyclerOptions<HomeTransactionModel> options = new FirestoreRecyclerOptions.Builder<HomeTransactionModel>()
                .setQuery(query,HomeTransactionModel.class)
                .build();

        home_adapter = new FirestoreRecyclerAdapter<HomeTransactionModel, HomeActivity.hometransViewHolder>(options) {
            @NonNull
            @Override
            public HomeActivity.hometransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list,parent,false);
                return new HomeActivity.hometransViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HomeActivity.hometransViewHolder holder, int position, @NonNull HomeTransactionModel model) {
                holder.textname.setText(model.getName());
                holder.textphone.setText(model.getPhone());
                holder.texthours.setText(model.getHours());
                holder.textminutes.setText(model.getMinutes());
                holder.texttotal_amount.setText(model.getTotal_amount());
                holder.textpaid_amount.setText(model.getPaid_amount());
            }
        };

        home_mfirestorelist.setHasFixedSize(true);
        home_mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        home_mfirestorelist.setAdapter(home_adapter);
        home_mfirestorelist.setItemAnimator(null);


    }

    private class hometransViewHolder extends RecyclerView.ViewHolder{
        private TextView textname;
        private TextView textphone;
        private TextView textminutes;
        private TextView texthours;
        private TextView texttotal_amount;
        private TextView textpaid_amount;

        public hometransViewHolder(@NonNull View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.card_name);
            textphone = itemView.findViewById(R.id.card_phone);
            texthours = itemView.findViewById(R.id.card_hour);
            textminutes = itemView.findViewById(R.id.card_minute);
            texttotal_amount = itemView.findViewById(R.id.card_total_amount);
            textpaid_amount = itemView.findViewById(R.id.card_paid_amount);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        home_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        home_adapter.stopListening();
    }




}