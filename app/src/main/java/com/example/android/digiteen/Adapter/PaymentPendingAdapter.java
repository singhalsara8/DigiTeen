package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.PaymentPending;
import com.example.android.digiteen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PaymentPendingAdapter extends RecyclerView.Adapter<PaymentPendingAdapter.PaymentPendingViewHolder> {
    private List<PaymentPending> list;
    private Context context;
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
    private String ownerBhawan, uid;

    public PaymentPendingAdapter(List<PaymentPending> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentPendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payment_pending,parent,false);
        PaymentPendingViewHolder paymentPendingViewHolder=new PaymentPendingViewHolder(view);
        return paymentPendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentPendingViewHolder holder, int position) {
        PaymentPending paymentPending=list.get(position);
        holder.mtoken.setText(paymentPending.getOwner_token_number());
        holder.mtotal.setText(String.valueOf(paymentPending.getOwner_grand_total()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentPendingViewHolder extends RecyclerView.ViewHolder{
        private TextView mtoken, mtotal;
        private ImageButton mdone,mclear;
        private PaymentPendingViewHolder(View view){
            super(view);
            mtotal=view.findViewById(R.id.owner_grand_total);
            mtoken=view.findViewById(R.id.owner_token_number);
            mdone=view.findViewById(R.id.owner_done);
            mclear=view.findViewById(R.id.owner_clear);
            mdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     final String token=list.get(getAdapterPosition()).getOwner_token_number();
                     Log.d("token",token);
                     final int total=list.get(getAdapterPosition()).getOwner_grand_total();
                    reference=databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ownerBhawan=dataSnapshot.child("bhawan").getValue(String.class);
                            reference1=databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token);
                            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    uid = dataSnapshot.child("ordered by").getValue(String.class);
                                    databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Approved").child(token).setValue(total);
                                    databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").setValue("Approved");
                                    databaseReference.child("user").child(uid).child("order").child(token).child("status").setValue("Approved");
                                    databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Payment Pending").removeValue();
                                    databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").child("Payment Pending").removeValue();
                                    databaseReference.child("user").child(uid).child("order").child(token).child("status").child("Payment Pending").removeValue();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            mclear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String token=list.get(getAdapterPosition()).getOwner_token_number();
                    Log.d("token",token);
                    final int total=list.get(getAdapterPosition()).getOwner_grand_total();
                    reference=databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ownerBhawan=dataSnapshot.child("bhawan").getValue(String.class);
                            reference1=databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token);
                            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    uid = dataSnapshot.child("ordered by").getValue(String.class);
                                    databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Payment Pending").removeValue();
                                    databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").child("Payment Pending").removeValue();
                                    databaseReference.child("user").child(uid).child("order").child(token).child("status").child("Payment Pending").removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }
}
