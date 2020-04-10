package in.ac.iitr.mdg.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import in.ac.iitr.mdg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import in.ac.iitr.mdg.Model.PaymentPending;
import in.ac.iitr.mdg.R;

public class PaymentPendingAdapter extends RecyclerView.Adapter<PaymentPendingAdapter.PaymentPendingViewHolder> {
    private List<PaymentPending> list;
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
    private String ownerBhawan, uid;
    private Bundle bundle;
    private NavController navController;
    private Activity activity;

    public PaymentPendingAdapter(List<PaymentPending> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PaymentPendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payment_pending, parent, false);
        PaymentPendingViewHolder paymentPendingViewHolder = new PaymentPendingViewHolder(view);
        return paymentPendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentPendingViewHolder holder, int position) {
        final PaymentPending paymentPending = list.get(position);
        holder.mtoken.setText(paymentPending.getOwner_token_number());
        holder.mtotal.setText(paymentPending.getOwner_grand_total() + "₹");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("tokenNumber", paymentPending.getOwner_token_number());
                navController = Navigation.findNavController(activity, R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_ownerOrderSummaryFragment_to_ownerOrderDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentPendingViewHolder extends RecyclerView.ViewHolder {
        private TextView mtoken, mtotal;
        private ImageView mdone, mclear;
        private RelativeLayout relativeLayout;

        private PaymentPendingViewHolder(View view) {
            super(view);
            relativeLayout = view.findViewById(R.id.payment_pending_layout);
            mtotal = view.findViewById(R.id.owner_grand_total);
            mtoken = view.findViewById(R.id.owner_token_number);
            mdone = view.findViewById(R.id.owner_done);
            mclear = view.findViewById(R.id.owner_clear);
            mdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CONFIRMATION");
                    builder.setMessage("Do you want to confirm order?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String token = list.get(getAdapterPosition()).getOwner_token_number();
                            Log.d("token", token);
                            final int total = list.get(getAdapterPosition()).getOwner_grand_total();
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
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Approved").child(token).setValue(total);
                                            databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").setValue("Approved");
                                            databaseReference.child("user").child(uid).child("order").child(token).child("status").setValue("Approved");
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Payment Pending").child(token).removeValue();
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
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });
            mclear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("CONFIRMATION");
                    builder.setMessage("Do you want to cancel order?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String token = list.get(getAdapterPosition()).getOwner_token_number();
                            Log.d("token", token);
                            final int total = list.get(getAdapterPosition()).getOwner_grand_total();
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
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Declined").child(token).setValue(total);
                                            databaseReference.child("bhawan").child(ownerBhawan).child("order").child(token).child("status").setValue("Declined");
                                            databaseReference.child("user").child(uid).child("order").child(token).child("status").setValue("Declined");
                                            databaseReference.child("bhawan").child(ownerBhawan).child("status").child("Payment Pending").child(token).removeValue();
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