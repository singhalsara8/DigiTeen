package com.example.android.digiteen.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
    private Activity activity;
    private List<PaymentPending> list;
    private String token;
    private int total;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
    private String ownerBhawan, uid;
    private Bundle bundle;
    private NavController navController;

    public ApprovedOrderAdapter(Context context, List<PaymentPending> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ApprovedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_approved_order, parent, false);
        ApprovedOrderViewHolder approvedOrderViewHolder = new ApprovedOrderViewHolder(view);
        return approvedOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedOrderViewHolder holder, int position) {
        final PaymentPending approved = list.get(position);
        holder.approved_token.setText(approved.getOwner_token_number());
        holder.aprroved_total.setText(approved.getOwner_grand_total() + "₹");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("tokenNumber", approved.getOwner_token_number());
                navController = Navigation.findNavController(activity, R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_ownerOrderSummaryFragment_to_ownerOrderDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ApprovedOrderViewHolder extends RecyclerView.ViewHolder {
        TextView approved_token, aprroved_total;
        ImageButton approved_done;
        RelativeLayout relativeLayout;

        private ApprovedOrderViewHolder(View view) {
            super(view);
            relativeLayout = view.findViewById(R.id.approved_order_layout);
            approved_token = view.findViewById(R.id.owner_approvedorder_token_number);
            aprroved_total = view.findViewById(R.id.owner_approvedorder_grand_total);
            approved_done = view.findViewById(R.id.owner_approvedorder_done);

            approved_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CONFIRMATION");
                    builder.setMessage("Is order completed?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            token = list.get(getAdapterPosition()).getOwner_token_number();
                            total = list.get(getAdapterPosition()).getOwner_grand_total();
                            reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ownerBhawan = dataSnapshot.child("bhawan").getValue(String.class);
                                    reference1 = databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token);
                                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            uid = dataSnapshot.child("ordered by").getValue(String.class);
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Completed").child(token).setValue(total);
                                            databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").setValue("Completed");
                                            databaseReference.child("user").child(uid).child("order").child(token).child("status").setValue("Completed");
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Approved").child(token).removeValue();
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
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
