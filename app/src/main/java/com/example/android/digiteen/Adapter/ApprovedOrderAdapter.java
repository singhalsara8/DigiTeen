package com.example.android.digiteen.Adapter;

import android.content.Context;
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

public class ApprovedOrderAdapter extends RecyclerView.Adapter<ApprovedOrderAdapter.ApprovedOrderViewHolder> {
    private Context context;
    private List<PaymentPending> list;
    private String token;
    private int total;
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
    private String ownerBhawan, uid;

    public ApprovedOrderAdapter(Context context, List<PaymentPending> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ApprovedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_approved_order,parent,false);
        ApprovedOrderViewHolder approvedOrderViewHolder=new ApprovedOrderViewHolder(view);
        return approvedOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedOrderViewHolder holder, int position) {
        PaymentPending approved=list.get(position);
        holder.approved_token.setText(approved.getOwner_token_number());
        holder.aprroved_total.setText(String.valueOf(approved.getOwner_grand_total()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ApprovedOrderViewHolder extends RecyclerView.ViewHolder{
        TextView approved_token, aprroved_total;
        ImageButton approved_done;
        private ApprovedOrderViewHolder(View view)
        {
            super(view);
            approved_token=view.findViewById(R.id.owner_approvedorder_token_number);
            aprroved_total=view.findViewById(R.id.owner_approvedorder_grand_total);
            approved_done=view.findViewById(R.id.owner_approvedorder_done);
            approved_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    token=list.get(getAdapterPosition()).getOwner_token_number();
                    total=list.get(getAdapterPosition()).getOwner_grand_total();
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
                                    databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Completed").child(token).setValue(total);
                                    databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").setValue("Completed");
                                    databaseReference.child("user").child(uid).child("order").child(token).child("status").setValue("Completed");
                                    databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Approved").removeValue();
                                    databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").child("Approved").removeValue();
                                    databaseReference.child("user").child(uid).child("order").child(token).child("status").child("Approved").removeValue();
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
