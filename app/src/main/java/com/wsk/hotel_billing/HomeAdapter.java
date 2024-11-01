package com.wsk.hotel_billing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private Context context;
    private List<HomeItem> items;
    private String search = "";

    public HomeAdapter(Context context, List<HomeItem> items) {
        this.context = context;
        this.items = items;
    }

    public void setSearch(String search) {
        this.search = search.toLowerCase();
        notifyDataSetChanged();
    }

    private OnHomeClickListener onHomeClickListener;
    public interface OnHomeClickListener {
        void onHomeClick(String id);
    }

    public void setOnHomeClickListener(OnHomeClickListener onHomeClickListener) {
        this.onHomeClickListener = onHomeClickListener;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(context).inflate(R.layout.view_home, parent, false));
    }
    public boolean performClickByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equals(name))
                if (HomeReader.readJsonDetails(context, items.get(i).getId()) != null) {
                    ((MainActivity) context).setFragment(new DetailsFragment(items.get(i).getId()));
                    return true;
                }
        }
        return false;
    }
    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, @SuppressLint("RecyclerView") int position) {
        if (items.get(position).getTitle().toLowerCase().contains(search)) {
            holder.itemView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT;
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = 0;
            layoutParams.width = 0;
            holder.itemView.setLayoutParams(layoutParams);
        }
        holder.photo.setImageResource(context.getResources().getIdentifier("i" + items.get(position).getPhoto().
                replace("cover/", "").replace(".jpg", ""), "drawable", context.getPackageName()));
        holder.title.setText(items.get(position).getTitle());
        String stars = "";
        for (int i = 0; i < 5; i++) {
            if (Float.parseFloat(items.get(position).getRating()) - i * 2 >= 0)
                stars = stars.concat("⭐");
        }
        holder.rating.setText(items.get(position).getRating() + stars);
        holder.distance.setText(items.get(position).getDescription() + " km from Alps' ski lift");
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHomeClickListener != null)
                    onHomeClickListener.onHomeClick(items.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HomeHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView title, rating, distance;
        private Button book;
        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            distance = itemView.findViewById(R.id.distance);
            book = itemView.findViewById(R.id.book);
        }
    }
}
