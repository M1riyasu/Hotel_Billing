package com.wsk.hotel_billing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    List<String> items;
    Context context;

    public UserAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.view_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.position.setText(position + 1 + "");
        String[] text = items.get(position).split(",");
        holder.price.setText(text[0]);
        holder.description.setText(text[1]);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private TextView position, description, price;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
        }
    }
}
