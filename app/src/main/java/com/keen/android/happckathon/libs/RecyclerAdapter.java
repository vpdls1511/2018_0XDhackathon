package com.keen.android.happckathon.libs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keen.android.happckathon.ListItemViewHolder;
import com.keen.android.happckathon.R;
import com.keen.android.happckathon.ViewList;

import java.util.ArrayList;

/**
 * Created by Kimhyeongmin on 2018. 1. 20..
 */

public class RecyclerAdapter extends RecyclerView.Adapter{

    ArrayList<ViewList> data;

    RecyclerAdapter(ArrayList<ViewList> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ListItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewList viewList = data.get(position);
        ListItemViewHolder item = (ListItemViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
