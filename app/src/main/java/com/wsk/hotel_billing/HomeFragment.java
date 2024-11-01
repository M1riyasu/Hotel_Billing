package com.wsk.hotel_billing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View r;
    private List<HomeItem> items = new ArrayList<>();
    private HomeAdapter adapter;
    private RecyclerView RV;
    private EditText search;
    private Button profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        r = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return r;
    }
    public void init() {
        RV = r.findViewById(R.id.RV);
        search = r.findViewById(R.id.search);
        profile = r.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).setFragment(new DetailsFragment(null));
            }
        });
        JSONArray array = HomeReader.readJson(getContext());
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                items.add(new HomeItem(
                        object.getString("hotel_id"),
                        object.getString("hotel_cover_image"),
                        object.getString("hotel_name"),
                        object.getString("hotel_rating"),
                        object.getString("hotel_to_ski_distance")
                ));
            }
        } catch (JSONException e) {
        }
        adapter = new HomeAdapter(getContext(), items);
        adapter.setOnHomeClickListener(new HomeAdapter.OnHomeClickListener() {
            @Override
            public void onHomeClick(String id) {
                if (HomeReader.readJsonDetails(getContext(), id) != null)
                    ((MainActivity) getContext()).setFragment(new DetailsFragment(id));
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.setSearch(s.toString());
            }
        });
        RV.setLayoutManager(new LinearLayoutManager(getContext()));
        RV.setAdapter(adapter);
    }
    public int getItemsSize() {
        return items.size();
    }
}