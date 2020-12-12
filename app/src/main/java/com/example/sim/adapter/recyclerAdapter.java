package com.example.sim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sim.Glide.Glide;
import com.example.sim.Glide.RequestListener;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.bean.picBean;

import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> {
    private List<picBean> list;
    private Context mContext;
    public recyclerAdapter(List<picBean> list,Context context) {
        this.list = list;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder hodler = new ViewHolder(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        picBean picBean = list.get(position);
        Glide.with(holder.pic.getContext())
                .load(picBean.getUrl())
                .loadError(picBean.getPic()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.item_recycler);
        }
    }
}
