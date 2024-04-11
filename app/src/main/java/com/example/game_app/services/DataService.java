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
    public String url = "https://api.rawg.io/api/games?key=150ca83dbba045dbbe31302938032a3f";
    private String nextURL;
    private String previousURL;
    private ArrayList<Game> arrGame = new ArrayList<>();
    public FetchDataListener fetchDataListener;
    private  int limit = 0;

    public DataService(FetchDataListener fetchDataListener) {
        this.fetchDataListener = fetchDataListener;
        this.arrGame = new  ArrayList<Game>();
        getArrGame(this.url);
    }

    public void getArrGame(String gURL) {
        arrGame = new ArrayList<Game>();
        String key  = "150ca83dbba045dbbe31302938032a3f";
        new Thread(new Runnable(){
            @Override
            public void run() {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    URL url = new URL(gURL);
                    HttpURLConnection request = (HttpURLConnection) url.openConnection();
                    request.connect();
                    JsonParser jp = new JsonParser();
                    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                    request.disconnect();
                    JsonObject rootObj = root.getAsJsonObject();
                    nextURL = rootObj.get("next").getAsString();
                    JsonArray resultsArray = rootObj.getAsJsonArray("results");
                    for (JsonElement je : resultsArray) {
                        JsonObject obj = je.getAsJsonObject();
                        String id = obj.get("id").getAsString();
                        String name = obj.get("name").getAsString();
                        String releaseDate = obj.get("released").getAsString();
                        String imgURL = obj.get("background_image").getAsString();
                        String rating = obj.get("rating").getAsString();
                        JsonArray genereArray = obj.getAsJsonArray("genres");
                        String genre = "";
                        for (JsonElement jeGenre : genereArray){
                            JsonObject genreObj = jeGenre.getAsJsonObject();
                            genre += genreObj.get("name").getAsString();
                            genre += ",";
                        }
//                      JsonElement jRoot = getGameExtraDetails(id, key);
//                      String description = getDescriptionById(jRoot);
//                      String developers = getDeveloperById(jRoot);
                        String description = "";
                        String developers = "";
                        Game game = new Game(name, releaseDate, imgURL,rating,genre,description,developers);
                        arrGame.add(game);
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        fetchDataListener.didFinishFetchingData(arrGame);
                    }
                });

            };
        }).start();
    }

    private JsonElement getGameExtraDetails(String id,String key)
    {
        try {
            URL url = new URL("https://api.rawg.io/api/games"+ "/"+ id +"?key=" + key);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            return root;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getNext() {

        if (limit < 3) {
            getArrGame(nextURL);
            limit ++;
        }
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
}