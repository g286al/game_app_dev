package com.example.game_app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.game_app.R;

import com.example.game_app.adapters.GameAdapter;
import com.example.game_app.models.Game;
import com.example.game_app.services.DataService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private ArrayList<Game> arrGame = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.r_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DataService and fetch game data
        DataService dataService = new DataService();
        arrGame = dataService.getArrGame();

        // Initialize and set GameAdapter to RecyclerView
        gameAdapter = new GameAdapter(this, arrGame);
        recyclerView.setAdapter(gameAdapter);
    }
}
