package com.example.game_app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game_app.R;
import com.example.game_app.models.Game;
import com.example.game_app.services.DataService;
import com.squareup.picasso.Picasso;

public class GameDetails extends Fragment {

    private int position;

    public GameDetails(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_details, container, false);

        // Retrieve the game object based on the position
        Game game = DataService.getClickedGame(position);

        // Bind views
        ImageView gameDetailImg = view.findViewById(R.id.gameDetailImg);
        TextView gameDetailName = view.findViewById(R.id.gameDetailName);
        TextView gameDetailRelease = view.findViewById(R.id.gameDetailRelease);
        TextView gameDetailRating = view.findViewById(R.id.gameDetailRating);
        TextView gameDetailGenre = view.findViewById(R.id.gameDetailGenre);
        TextView gameDetailDescription = view.findViewById(R.id.gameDetailDescription);

        Picasso.get().load(game.getImageUrl()).into(gameDetailImg);
        // Set text values
        gameDetailName.setText(game.getName());
        gameDetailRelease.setText(game.getReleaseDate());
        gameDetailRating.setText(game.getRating());
        gameDetailGenre.setText(game.getGenre());

        return view;
    }
}