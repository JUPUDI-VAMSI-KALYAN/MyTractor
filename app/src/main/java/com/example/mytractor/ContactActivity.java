package com.example.mytractor;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ContactActivity extends AppCompatActivity {


    private RecyclerView c_mfirestorelist;
    private FirebaseFirestore c_firebaseFirestore;
    private FirestoreRecyclerAdapter c_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Objects.requireNonNull(getSupportActionBar()).hide();
        c_mfirestorelist = findViewById(R.id.recycleview_contact);
        c_firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = c_firebaseFirestore.collection("users");
        FirestoreRecyclerOptions<ContactModel> options = new FirestoreRecyclerOptions.Builder<ContactModel>()
                .setQuery(query,ContactModel.class)
                .build();

        c_adapter = new FirestoreRecyclerAdapter<ContactModel, ContactActivity.contactViewHolder>(options) {
            @NonNull
            @Override
            public ContactActivity.contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_list,parent,false);
                return new ContactActivity.contactViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ContactActivity.contactViewHolder holder, int position, @NonNull ContactModel model) {
                holder.textname.setText(model.getName());
                holder.textphone.setText(model.getPhone());
                holder.call_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = "tel:"+model.getPhone();
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse(s));
                        startActivity(i);
                    }
                });


            }
        };

        c_mfirestorelist.setHasFixedSize(true);
        c_mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        c_mfirestorelist.setAdapter(c_adapter);

    }
    private class contactViewHolder extends RecyclerView.ViewHolder{
        private TextView textname;
        private TextView textphone;
        private ImageView call_btn;


        public contactViewHolder(@NonNull View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.contactcard_name);
            textphone = itemView.findViewById(R.id.contactcard_phone);
            call_btn = itemView.findViewById(R.id.contactcard_call_btn);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        c_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        c_adapter.stopListening();
    }




}