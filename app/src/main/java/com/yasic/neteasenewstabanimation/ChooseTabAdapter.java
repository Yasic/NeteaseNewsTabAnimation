package com.yasic.neteasenewstabanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yasic on 2016/5/30.
 */
public class ChooseTabAdapter extends RecyclerView.Adapter<ChooseTabAdapter.MyViewHolder> implements onMoveAndSwipeListener {
    private Context context;
    private List<String> typeList = new ArrayList<>();
    //private onAllTabsListener listener;

    public ChooseTabAdapter(Context context, List<String> typeList){
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /*holder.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.allTabsItemClick(holder.itemView, position);
            }
        });*/
        holder.tvType.setText(typeList.get(position));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(typeList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        typeList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tv_Type);
        }
    }

    /*public void setListener(onAllTabsListener listener){
        this.listener = listener;
    }

    public interface onAllTabsListener {
        void allTabsItemClick(View view, int position);
    }*/
}
