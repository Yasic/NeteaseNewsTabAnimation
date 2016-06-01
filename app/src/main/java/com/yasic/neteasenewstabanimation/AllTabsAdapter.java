package com.yasic.neteasenewstabanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasic on 2016/5/30.
 */
public class AllTabsAdapter extends RecyclerView.Adapter<AllTabsAdapter.MyViewHoler> {
    private Context context;
    private List<String> typeList = new ArrayList<>();
    private onAllTabsListener listener;

    public AllTabsAdapter(Context context, List<String> typeList){
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHoler(LayoutInflater.from(context).inflate(R.layout.item_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHoler holder, final int position) {
        holder.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.allTabsItemClick(holder.itemView, position);
            }
        });
        holder.tvType.setText(typeList.get(position));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {
        private TextView tvType;
        public MyViewHoler(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tv_Type);
        }
    }

    public void setListener(onAllTabsListener listener){
        this.listener = listener;
    }
}
