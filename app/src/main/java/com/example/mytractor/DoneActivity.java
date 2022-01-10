package com.example.mytractor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DoneActivity extends AppCompatActivity {

    private RecyclerView donefirestorelist;
    private FirebaseFirestore donefirebaseFirestore;
    private FirestoreRecyclerAdapter doneadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);


        donefirestorelist = findViewById(R.id.done_recycleview);
        donefirebaseFirestore = FirebaseFirestore.getInstance();

        Query query = donefirebaseFirestore.collection("transactions").whereEqualTo("fully_paid","true");
        FirestoreRecyclerOptions<DoneTransactionModel> doneoptions = new FirestoreRecyclerOptions.Builder<DoneTransactionModel>()
                .setQuery(query, snapshot -> {
                    DoneTransactionModel doneTransactionModel = snapshot.toObject(DoneTransactionModel.class);
                    String itemid = snapshot.getId();
                    doneTransactionModel.setItem_id(itemid);
                    return doneTransactionModel;
                })
                .build();


        doneadapter = new FirestoreRecyclerAdapter<DoneTransactionModel, DoneActivity.donetransViewHolder>(doneoptions) {
            @NonNull
            @Override
            public DoneActivity.donetransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list,parent,false);
                return new DoneActivity.donetransViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DoneActivity.donetransViewHolder holder, int position, @NonNull DoneTransactionModel model) {
                holder.textname.setText(model.getName());
                holder.textphone.setText(model.getPhone());
                holder.texthours.setText(model.getHours());
                holder.textminutes.setText(model.getMinutes());
                holder.texttotal_amount.setText(model.getTotal_amount());
                holder.textpaid_amount.setText(model.getPaid_amount());

            }
        };

        donefirestorelist.setHasFixedSize(true);
        donefirestorelist.setLayoutManager(new LinearLayoutManager(this));
        donefirestorelist.setAdapter(doneadapter);


    }



    private class donetransViewHolder extends RecyclerView.ViewHolder{
        private TextView textname;
        private TextView textphone;
        private TextView textminutes;
        private TextView texthours;
        private TextView texttotal_amount;
        private TextView textpaid_amount;

        public donetransViewHolder(@NonNull View itemView) {
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
        doneadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doneadapter.stopListening();
    }









}