package com.example.game_app.services;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import com.example.game_app.adapters.FetchDataListener;
import com.example.game_app.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataService {
    public String url = "https://api.rawg.io/api/games?key=b4c22d9f1feb4090bd453c30bc2d4e9a";
    private final String initialUrl = "https://api.rawg.io/api/games?key=b4c22d9f1feb4090bd453c30bc2d4e9a";
    private String nextURL;
    private String key  = "b4c22d9f1feb4090bd453c30bc2d4e9a";
    private String previousURL;
    private ArrayList<Game> arrGame = new ArrayList<>();
    public FetchDataListener fetchDataListener;
    private  int limit = 0;
    private DataFetchListener dataFetchListener;
    private boolean isLoading = false;

    public DataService(DataFetchListener dataFetchListener) {
        this.dataFetchListener = dataFetchListener;
    }

    public DataService(FetchDataListener fetchDataListener) {
        this.fetchDataListener = fetchDataListener;
        this.arrGame = new  ArrayList<Game>();
        getArrGame(this.url);
    }



        public void getArrGame(String url) {
            if (isLoading) return;
            isLoading = true;

            new Thread(() -> {
                try {
                    URL apiUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                    JsonParser parser = new JsonParser();
                    JsonElement root = parser.parse(new InputStreamReader(connection.getInputStream()));
                    JsonObject rootObj = root.getAsJsonObject();
                    nextURL = rootObj.get("next").getAsString();
                    JsonArray results = rootObj.getAsJsonArray("results");
                    ArrayList<Game> games = parseGames(results);
                    new Handler(Looper.getMainLooper()).post(() -> fetchDataListener.didFinishFetchingData(games));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isLoading = false;
                }
            }).start();
        }

    private ArrayList<Game> parseGames(JsonArray results) {
        ArrayList<Game> games = new ArrayList<>();
        for (JsonElement element : results) {
            JsonObject gameObj = element.getAsJsonObject();
            String id = gameObj.get("id").getAsString();
            // Check if game already exists
            if (gameExists(id)) continue;  // Skip adding this game if it already exists
            String name = gameObj.get("name").getAsString();
            String releaseDate = gameObj.get("released").getAsString();
            String imgUrl = gameObj.get("background_image").getAsString();
            String rating = gameObj.get("rating").getAsString();
            String genre = parseGenres(gameObj.getAsJsonArray("genres"));
            games.add(new Game(id, name, releaseDate, imgUrl, rating, genre));
        }
        return games;
    }

    private boolean gameExists(String id) {
        for (Game game : arrGame) {
            if (game.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private String parseGenres(JsonArray genres) {
        StringBuilder genreBuilder = new StringBuilder();
        for (JsonElement genreElement : genres) {
            JsonObject genreObj = genreElement.getAsJsonObject();
            if (genreBuilder.length() > 0) genreBuilder.append(", ");
            genreBuilder.append(genreObj.get("name").getAsString());
        }
        return genreBuilder.toString();
    }

    public boolean hasNext() {
        return nextURL != null && !nextURL.isEmpty();
    }

    public void getNext() {
        if (hasNext() && !isLoading) getArrGame(nextURL);
    }

    public String getInitialUrl() {
        return initialUrl;
    }



    public void setFetchDataListener(FetchDataListener listener) {
        this.fetchDataListener = listener;
    }



    private String getDescriptionById(JsonElement root) {
        JsonObject rootObj = root.getAsJsonObject();
        String description = rootObj.get("description").getAsString();
        String  encodedString = StringEscapeUtils.escapeJava(description);
        encodedString = encodedString.replaceAll("<p>|<br />", "");
        int index = encodedString.indexOf(".</p>\\nEspa\\u00F1ol");
        encodedString = (index != -1) ? encodedString.substring(0, index) : encodedString;
        return StringEscapeUtils.unescapeJava(encodedString);
    }

    private String getDeveloperById(JsonElement root) {
        JsonObject rootObj = root.getAsJsonObject();
        JsonArray developersarray = rootObj.getAsJsonArray("developers");
        String developers = "";
        for (int i = 0; i < developersarray.size(); i++) {
            JsonObject genreObj = developersarray.get(i).getAsJsonObject();
            developers += genreObj.get("name").getAsString();
            if (i < developersarray.size() - 1) {
                developers += ", ";
            }
        }
        return developers;
    }

    public void GatherMoreInfo(Game chosenGame) {
        if (!chosenGame.AlreadyClicked()) {
            new Thread(() -> {
                try {
                    URL url = new URL("https://api.rawg.io/api/games" + "/" + chosenGame.getId() + "?key=" + key);
                    HttpURLConnection request = (HttpURLConnection) url.openConnection();
                    request.connect();
                    JsonParser jp = new JsonParser();
                    JsonElement root = jp.parse(new InputStreamReader(request.getInputStream()));
                    chosenGame.setDescription(getDescriptionById(root));
                    chosenGame.setDevelopers(getDeveloperById(root));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
