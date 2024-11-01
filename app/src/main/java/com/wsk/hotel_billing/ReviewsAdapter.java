package com.wsk.hotel_billing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {
    private Context context;
    private List<ReviewsItem> itemList;

    public ReviewsAdapter(Context context, List<ReviewsItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewsHolder(LayoutInflater.from(context).inflate(R.layout.view_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder holder, int position) {
        holder.nameLetter.setText(itemList.get(position).getName().substring(0,1));
        holder.name.setText(itemList.get(position).getName());
        holder.review.setText(itemList.get(position).getReview());
        holder.city.setText(itemList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {
        private TextView nameLetter, name, city, review;
        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            nameLetter = itemView.findViewById(R.id.nameLetter);
            name = itemView.findViewById(R.id.name);
            city = itemView.findViewById(R.id.city);
            review = itemView.findViewById(R.id.review);
        }
    }
}
