package com.wsk.hotel_billing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {
    private View r;
    private String id;
    public DetailsFragment(String id) {
        this.id = id;
        if (id == null) {
            isUser = true;
        }
    }
    private boolean isUser = false;
    private TextView location, staff, value, name, room, cash, formName;
    private EditText firstName, lastName, inDate, outDate, adults, children;
    private RadioButton noBusiness, business;
    private RadioGroup pay;
    private String payS;
    private RoomsItem roomsItem;
    private SharedPreferences sharedPreferences;
    private SeekBar sLocation, sStaff, sValue;
    private DetailsItem item;
    private RecyclerView reviews, rooms, bookings;
    private Button roomButton, reviewsButton, back, book;
    private LinearLayout l0, l1, l2, l3;
    private ReviewsAdapter reviewsAdapter;
    private RoomsAdapter roomsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        r = inflater.inflate(R.layout.fragment_details, container, false);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l2.getVisibility() == View.VISIBLE) {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                } else {
                    ((MainActivity) getContext()).setFragment(new HomeFragment());
                }
            }
        });

        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l0.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
                roomButton.setTextColor(0xFFFF0000);
                reviewsButton.setTextColor(0xFF000000);
            }
        });
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l0.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
                roomButton.setTextColor(0xFF000000);
                reviewsButton.setTextColor(0xFFFF0000);
            }
        });
        reviews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rooms.setLayoutManager(new LinearLayoutManager(getContext()));
        if (id != null) {
            JSONObject object = HomeReader.readJsonDetails(getContext(), id);
            List<String> categories = new ArrayList<>();
            List<String> features = new ArrayList<>();
            List<ReviewsItem> reviewsItems = new ArrayList<>();
            List<RoomsItem> roomsItems = new ArrayList<>();
            try {
                for (int i = 0; i < object.getJSONObject("guest_reviews").getJSONArray("ratings_categories").length(); i++) {
                    JSONArray ratingsArray = object.getJSONObject("guest_reviews").getJSONArray("ratings_categories");
                    for (int j = 0; j < ratingsArray.length(); j++) {
                        JSONObject rating = ratingsArray.getJSONObject(j);
                        categories.add(rating.optString(rating.keys().next()));
                    }
                }
            } catch (JSONException e) {
            }
            try {
                for (int i = 0; i < object.getJSONArray("rooms").length(); i++) {
                    features.add(
                            object.getJSONArray("rooms").getJSONObject(i).getJSONArray("room_features").getString(i)
                    );
                }
            } catch (JSONException e) {
            }
            try {
                for (int i = 0; i < object.getJSONArray("rooms").length(); i++) {
                    roomsItems.add(new RoomsItem(
                            object.getJSONArray("rooms").getJSONObject(i).getString("room_id"),
                            object.getJSONArray("rooms").getJSONObject(i).getString("room_type"),
                            object.getJSONArray("rooms").getJSONObject(i).getString("room_bed_type"),
                            object.getJSONArray("rooms").getJSONObject(i).getString("room_total_number_of_guests"),
                            features,
                            object.getJSONArray("rooms").getJSONObject(i).getString("room_price_for_one_night")
                    ));
                }
            } catch (JSONException e) {
            }
            try {
                for (int i = 0; i < object.getJSONObject("guest_reviews").getJSONArray("reviews_objects").length(); i++) {
                    reviewsItems.add(new ReviewsItem(
                            object.getJSONObject("guest_reviews").getJSONArray("reviews_objects").getJSONObject(i).getString("username"),
                            object.getJSONObject("guest_reviews").getJSONArray("reviews_objects").getJSONObject(i).getString("country"),
                            object.getJSONObject("guest_reviews").getJSONArray("reviews_objects").getJSONObject(i).getString("review_text")
                    ));
                }
            } catch (JSONException e) {
            }

            try {
                item = new DetailsItem(
                        object.getString("hotel_id"),
                        object.getString("hotel_name"),
                        categories,
                        reviewsItems,
                        roomsItems
                );
            } catch (JSONException e) {
            }
            name.setText(item.getTitle());

            reviewsAdapter = new ReviewsAdapter(getContext(), item.getReview());

            roomsAdapter = new RoomsAdapter(getContext(), item.getRoom());

            roomsAdapter.setOnRoomClickListener(new RoomsAdapter.OnRoomClickListener() {
                @Override
                public void onRoomClick(RoomsItem item) {
                    roomsItem = item;
                    cash.setText("€ " + (Integer.parseInt(roomsItem.getRoomPriceForNight().replace("€", "").replace(" ", ""))));
                    l2.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);
                    room.setText("1");
                    initEdit(firstName, lastName, inDate, outDate, adults, children);
                    noBusiness.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            business.setChecked(false);
                            noBusiness.setChecked(true);
                            cash.setText("€ " + (Integer.parseInt(item.getRoomPriceForNight().replace("€", "").replace(" ", "")) + (business.isChecked() ? 150 : 0)));
                        }
                    });
                    business.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            business.setChecked(true);
                            noBusiness.setChecked(false);
                            cash.setText("€ " + (Integer.parseInt(item.getRoomPriceForNight().replace("€", "").replace(" ", "")) + (business.isChecked() ? 150 : 0)));
                        }
                    });
                    pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton button = r.findViewById(checkedId);
                            payS = button.getText().toString();
                        }
                    });

                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bookNow();
                        }
                    });
                }
            });

            reviews.setAdapter(reviewsAdapter);

            rooms.setAdapter(roomsAdapter);

            location.setText(item.getRating().get(0));
            staff.setText(item.getRating().get(1));
            value.setText(item.getRating().get(2));
            sLocation.setProgress((int) (Float.parseFloat(item.getRating().get(0)) * 10));
            sStaff.setProgress((int) (Float.parseFloat(item.getRating().get(1)) * 10));
            sValue.setProgress((int) (Float.parseFloat(item.getRating().get(2)) * 10));
        }
        return r;
    }
    private void init() {
        sharedPreferences = getContext().getSharedPreferences("My", Context.MODE_PRIVATE);
        reviews = r.findViewById(R.id.reviews);
        book = r.findViewById(R.id.book);
        rooms = r.findViewById(R.id.rooms);
        name = r.findViewById(R.id.name);
        location = r.findViewById(R.id.location);
        staff = r.findViewById(R.id.staff);
        value = r.findViewById(R.id.value);
        sLocation = r.findViewById(R.id.sLocation);
        sStaff = r.findViewById(R.id.sStaff);
        sValue = r.findViewById(R.id.sValue);
        roomButton = r.findViewById(R.id.roomButton);
        reviewsButton = r.findViewById(R.id.reviewsButton);
        firstName = r.findViewById(R.id.firstName);
        lastName = r.findViewById(R.id.lastName);
        inDate = r.findViewById(R.id.inDate);
        outDate = r.findViewById(R.id.outDate);
        formName = r.findViewById(R.id.formName);
        adults = r.findViewById(R.id.adults);
        children = r.findViewById(R.id.children);
        room = r.findViewById(R.id.room);
        cash = r.findViewById(R.id.cash);
        business = r.findViewById(R.id.business);
        noBusiness = r.findViewById(R.id.noBusiness);
        pay = r.findViewById(R.id.pay);
        back = r.findViewById(R.id.back);
        bookings = r.findViewById(R.id.bookings);
        l0 = r.findViewById(R.id.l0);
        l1 = r.findViewById(R.id.l1);
        l2 = r.findViewById(R.id.l2);
        l3 = r.findViewById(R.id.l3);

        if (isUser) {
            name.setVisibility(View.GONE);
            reviewsButton.setVisibility(View.GONE);
            roomButton.setVisibility(View.GONE);
            formName.setText(name.getText().toString());
            l3.setVisibility(View.VISIBLE);
            List<String> items = new ArrayList<>();
            for (int i = 0; sharedPreferences.getString("TOTAL" + i, null) != null; i++) {
                l2 = r.findViewById(R.id.l2);
                items.add(sharedPreferences.getString("TOTAL" + i, null));
            }
            System.out.println(sharedPreferences.getString("TOTAL" + 0, null));
            UserAdapter userAdapter = new UserAdapter(items, getContext());
            bookings.setLayoutManager(new LinearLayoutManager(getContext()));
            bookings.setAdapter(userAdapter);
        }
    }
    public int getItemsCountReviews() {
        return reviewsAdapter.getItemCount();
    }
    public int getItemsCountRooms() {
        return roomsAdapter.getItemCount();
    }
    public void performClick(String name) {
        for (int i = 0; i < pay.getChildCount(); i++) {
            RadioButton button = r.findViewById(pay.getChildAt(i).getId());
            if (button.getText().toString().toLowerCase().equals(name.toLowerCase())) {
                button.performClick();
            }
        }
    }
    public boolean isValidDateFormat(String date) {
        String format1 = "MM-dd-yyyy";
        String format2 = "MMM dd yyyy";

        return isDateFormat(date, format1) || isDateFormat(date, format2);
    }
    private static boolean isDateFormat(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public void bookNow() {
        if (isValidDateFormat(inDate.getText().toString()) && isValidDateFormat(outDate.getText().toString())) {
            new AlertDialog.Builder(getContext()).setMessage("Are you sure you want to book this hotel?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int checked = business.isChecked() ? 150 : 0;
                            String isBusiness = business.isChecked() ? business.getText().toString() : noBusiness.getText().toString();
                            cash.setText("€ " + (Integer.parseInt(roomsItem.getRoomPriceForNight().replace("€", "").replace(" ", "")) + checked));
                            String total = cash.getText() + "," +
                                    firstName.getText().toString() + " " +
                                    lastName.getText().toString() + "\n" +
                                    name.getText().toString() + "\n" +
                                    inDate.getText().toString() + " " +
                                    outDate.getText().toString() + "\n" +
                                    adults.getText().toString() + " Adults " +
                                    children.getText().toString() + " Children" + " 1 Room" + "\n" +
                                    isBusiness + " " + payS;
                            int index = 0;
                            for (int i = 0; sharedPreferences.getString("TOTAL" + i, null) != null; i++) {
                                index = i + 1;
                            }
                            sharedPreferences.edit().putString("TOTAL" + index, total).apply();
                            l2.setVisibility(View.GONE);
                            l1.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setMessage("Wrong format!")
                    .setCancelable(false)
                    .create();

            alertDialog.show();

            // Закрываем диалог через 2 секунды
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
            }, 2000);
        }
    }
    private void initEdit(EditText... edits) {
        for (EditText edit :
                edits) {
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
}