package com.example.mytractor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.Objects;

public class PendingActivity extends AppCompatActivity {

    private RecyclerView mfirestorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mfirestorelist = findViewById(R.id.recycleview_pending);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("transactions").whereEqualTo("fully_paid","false").limit(50).orderBy("timestamp");
        FirestoreRecyclerOptions<TransactionModel> options = new FirestoreRecyclerOptions.Builder<TransactionModel>()
                .setQuery(query, snapshot -> {
                    TransactionModel TransactionModel = snapshot.toObject(TransactionModel.class);
                    String itemid = snapshot.getId();
                    TransactionModel.setItemId(itemid);
                    return TransactionModel;
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<TransactionModel,transViewHolder>(options) {
            @NonNull
            @Override
            public transViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list,parent,false);
                return new transViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull transViewHolder holder, int position, @NonNull TransactionModel model) {
                holder.textname.setText(model.getName());
                holder.textphone.setText(model.getPhone());
                holder.texthours.setText(model.getHours());
                holder.textminutes.setText(model.getMinutes());
                holder.texttotal_amount.setText(model.getTotal_amount());
                holder.textpaid_amount.setText(model.getPaid_amount());
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(),EditDataActivity.class);
                        i.putExtra("name",model.getName());
                        i.putExtra("item_id",model.getItemId());
                        i.putExtra("phone",model.getPhone());
                        i.putExtra("hours",model.getHours());
                        i.putExtra("minutes",model.getMinutes());
                        i.putExtra("total_amount",model.getTotal_amount());
                        i.putExtra("paid_amount",model.getPaid_amount());
                        Toast.makeText( getApplicationContext(), "Btn Clicked "+ model.getItemId(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                });

            }
        };

        mfirestorelist.setHasFixedSize(true);
        mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mfirestorelist.setAdapter(adapter);


    }

    private class transViewHolder extends RecyclerView.ViewHolder{
        private TextView textname;
        private TextView textphone;
        private TextView textminutes;
        private TextView texthours;
        private TextView texttotal_amount;
        private TextView textpaid_amount;
        private Button btn;

        public transViewHolder(@NonNull View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.card_name);
            textphone = itemView.findViewById(R.id.card_phone);
            texthours = itemView.findViewById(R.id.card_hour);
            textminutes = itemView.findViewById(R.id.card_minute);
            texttotal_amount = itemView.findViewById(R.id.card_total_amount);
            textpaid_amount = itemView.findViewById(R.id.card_paid_amount);
            btn = itemView.findViewById(R.id.cardchangebutton);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}