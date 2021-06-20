package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.GameViewAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        RecyclerView r = v.findViewById(R.id.recycler_game);
        r.setAdapter(new GameViewAdapter(MainActivity.main));
        r.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
}