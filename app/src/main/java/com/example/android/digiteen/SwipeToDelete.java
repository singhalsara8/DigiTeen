package com.example.android.digiteen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

abstract public class SwipeToDelete extends ItemTouchHelper.Callback {

    private Context context;
    private Paint mclearPaint;
    private ColorDrawable mbackground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;


    public SwipeToDelete(Context context) {
        this.context = context;
        mclearPaint=new Paint();
        mclearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mbackground=new ColorDrawable();
        backgroundColor= Color.parseColor("#b80f0a");
        deleteDrawable= ContextCompat.getDrawable(context,R.drawable.ic_delete);
        intrinsicWidth=deleteDrawable.getIntrinsicWidth();
        intrinsicHeight=deleteDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View view=viewHolder.itemView;
        int itemHeight=view.getHeight();
        boolean isCancelled=dX==0 && !isCurrentlyActive;

        if(isCancelled){
            clearCanvas(c,view.getRight()+dX,(float) view.getTop(),(float) view.getRight(),(float) view.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        mbackground.setColor(backgroundColor);
        mbackground.setBounds(view.getRight() + (int) dX, view.getTop(), view.getRight(), view.getBottom());
        mbackground.draw(c);

        int deleteIconTop = view.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = view.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = view.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteDrawable.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


    }

    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom){
        c.drawRect(left,top,right,bottom,mclearPaint);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.4f;
    }
}
