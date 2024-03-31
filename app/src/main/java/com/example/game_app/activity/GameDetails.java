package com.example.game_app.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.game_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameDetails extends Fragment {

    public GameDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static GameDetails newInstance(String param1, String param2) {
        GameDetails fragment = new GameDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false);
    }
}