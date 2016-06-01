package com.yasic.neteasenewstabanimation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Yasic on 2016/5/30.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private onMoveAndSwipeListener onMoveAndSwipeListener;

    public MyItemTouchHelperCallback(onMoveAndSwipeListener onMoveAndSwipeListener){
        this.onMoveAndSwipeListener = onMoveAndSwipeListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        final int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()){
            return false;
        }
        onMoveAndSwipeListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onMoveAndSwipeListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
