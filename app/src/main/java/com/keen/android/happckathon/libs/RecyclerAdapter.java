package com.keen.android.happckathon.libs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keen.android.happckathon.R;
import com.keen.android.happckathon.ui.fragments.BoardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimhyeongmin on 2018. 1. 20..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    Context context;
    List<Item> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<Item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        final Item item=items.get(position);

        Glide.with(context)
                .load(item.boardImage)
                .into(holder.boardImage);

        holder.boardTitle.setText(item.boardTitle);
        holder.boardLocation.setText(item.boardLoation);
        holder.boardDate.setText(item.boardDate);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView boardImage;
        TextView boardTitle, boardLocation, boardDate;

        public ViewHolder(View itemView) {
            super(itemView);

            boardImage = itemView.findViewById(R.id.boardMainImage);
            boardTitle = itemView.findViewById(R.id.boardTitle);
            boardLocation = itemView.findViewById(R.id.boardLocation);
            boardDate = itemView.findViewById(R.id.boardDate);

        }
    }
}
