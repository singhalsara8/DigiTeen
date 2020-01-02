package com.example.android.digiteen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.digiteen.Model.OwnerMenu;
import com.example.android.digiteen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OwnerMenuAdapter extends RecyclerView.Adapter<OwnerMenuAdapter.OwnerMenuViewHolder> {
    private List<OwnerMenu> ownerMenus;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, reference;
    private String ownerbhawan;
    private String itemName;
    private int itemPrice;

    public OwnerMenuAdapter(List<OwnerMenu> ownerMenus, Context context) {
        this.ownerMenus = ownerMenus;
        this.context = context;
    }

    @NonNull
    @Override
    public OwnerMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_owner_menu, parent, false);
        OwnerMenuViewHolder ownerMenuViewHolder = new OwnerMenuViewHolder(view);
        return ownerMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnerMenuViewHolder holder, final int position) {
        final OwnerMenu ownerMenu = ownerMenus.get(position);
        holder.itemname.setText(ownerMenu.getItemname());
        holder.itemprice.setText(ownerMenu.getItemprice()+"₹");
        //Glide.with(context).load(ownerMenu.getUri()).into(holder.imageView);
        //holder.imageView.setImageURI();
    }

    @Override
    public int getItemCount() {
        return ownerMenus.size();
    }

    public List<OwnerMenu> getList(){
        return ownerMenus;
    }

    public void removeItem(final int position){
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerbhawan = dataSnapshot.getValue(String.class);
                itemName = ownerMenus.get(position).getItemname();
                databaseReference.child("bhawan").child(ownerbhawan).child("Menu").child(itemName).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void restoreItem(final OwnerMenu item){
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        reference = databaseReference.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("profile").child("bhawan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerbhawan = dataSnapshot.getValue(String.class);
                itemName = item.getItemname();
                itemPrice=item.getItemprice();
                databaseReference.child("bhawan").child(ownerbhawan).child("Menu").child(itemName).setValue(itemPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class OwnerMenuViewHolder extends RecyclerView.ViewHolder {
        private TextView itemname, itemprice;
        private ImageView imageView;

        private OwnerMenuViewHolder(View view) {
            super(view);
            itemname = view.findViewById(R.id.owner_menu_itemname);
            itemprice = view.findViewById(R.id.owner_menu_itemprice);
            imageView=view.findViewById(R.id.owner_fetch_image);
        }

    }
}
