package com.example.game_app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.game_app.adapters.FetchDataListener;
import com.example.game_app.models.Game;
import com.example.game_app.services.DataService;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel implements FetchDataListener {
    public MutableLiveData<ArrayList<Game>> arrGame = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Game>> newItems = new MutableLiveData<>();
    private DataService dataService;

    public MainActivityViewModel() {
        this.dataService = new DataService(this);
    }

    @Override
    public void didFinishFetchingData(ArrayList<Game> gameList) {
        if(arrGame.getValue() == null) {
            arrGame.setValue(new ArrayList<>());
        }
        ArrayList<Game> tempList = arrGame.getValue();
        if (tempList != null) {
            tempList.addAll(gameList);
        }
        arrGame.setValue(tempList);
        newItems.setValue(gameList);
    }
    public void fetchMoreData(){
        dataService.getNext();
    }
    public Game getGameByPos(int position){
        return arrGame.getValue().get(position);
    }
}
