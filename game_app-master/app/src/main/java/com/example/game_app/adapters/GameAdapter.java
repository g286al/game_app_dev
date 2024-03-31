package com.example.game_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import com.example.game_app.models.Game;
import com.example.game_app.R;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Game> arrGame;

    public GameAdapter(Context context, ArrayList<Game> arrGame) {
        this.context = context;
        this.arrGame = arrGame;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.r_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Game game = arrGame.get(position);
        holder.name.setText(game.getName());
        holder.release.setText(game.getReleaseDate());
        Picasso.get().load(game.getImageUrl()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return arrGame.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, release;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textViewName);
            release = itemView.findViewById(R.id.textViewRelease);
        }
    }
}