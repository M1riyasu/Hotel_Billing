package com.wsk.hotel_billing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsHolder> {
    private Context context;
    private List<RoomsItem> items;
    private OnRoomClickListener onRoomClickListener;
    private RoomsHolder hold = null;
    public interface OnRoomClickListener {
        void onRoomClick(RoomsItem item);
    }

    public void setOnRoomClickListener(OnRoomClickListener onRoomClickListener) {
        this.onRoomClickListener = onRoomClickListener;
    }

    public RoomsAdapter(Context context, List<RoomsItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RoomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomsHolder(LayoutInflater.from(context).inflate(R.layout.view_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.roomType.setText(items.get(position).getRoomType());
        holder.bedTypeGuestsNumber.setText("Bed: " + items.get(position).getRoomBedType() + " Total number of guests: " + items.get(position).getRoomTotalNumberOfGuests());
        List<String> features = items.get(position).getRoomFeatures();
        StringBuilder featuresString = new StringBuilder();
        for (String feature : features) {
            featuresString.append(feature).append(", ");
        }
        if (featuresString.length() > 0) {
            featuresString.setLength(featuresString.length() - 2);
        }
        holder.features.setText(featuresString.toString());
        holder.price.setText("€ " + items.get(position).getRoomPriceForNight());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRoomClickListener != null) onRoomClickListener.onRoomClick(items.get(position));
            }
        });
        if (hold == null)
            hold = holder;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public boolean performClickByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getRoomType().toLowerCase().equals(name.toLowerCase())) {
                hold.itemView.performClick();
                return true;
            }
        }
        return false;
    }
    public class RoomsHolder extends RecyclerView.ViewHolder {
        private TextView roomType, bedTypeGuestsNumber, features, price;
        public RoomsHolder(@NonNull View itemView) {
            super(itemView);
            roomType = itemView.findViewById(R.id.roomType);
            bedTypeGuestsNumber = itemView.findViewById(R.id.bedTypeGuestsNumber);
            features = itemView.findViewById(R.id.features);
            price = itemView.findViewById(R.id.price);
        }
    }
}
