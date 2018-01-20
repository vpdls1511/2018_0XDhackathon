package com.keen.android.happckathon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 강성준 on 2018-01-20.
 */

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    TextView title, date, text;
    ImageView image;

    public ListItemViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.list_title);
        date = itemView.findViewById(R.id.list_date);
        image = itemView.findViewById(R.id.list_image);
        text = itemView.findViewById(R.id.list_text);
    }
}
