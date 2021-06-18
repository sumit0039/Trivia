package com.alibi.triviaapp.repositories;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibi.triviaapp.localdatabase.DbManager;
import com.alibi.triviaapp.model.GameDetails;

import java.util.ArrayList;
import java.util.List;

public class HistoryRepo {


    private final MutableLiveData<List<GameDetails>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<GameDetails>> getHistoryData(Context context) {
        if (mutableLiveData.getValue() == null) {
            initHistorySection();
            fetchAllHistory(context);
        }
        return mutableLiveData;
    }

    private void fetchAllHistory(Context context) {
        List<GameDetails> allHistoryDetails = new ArrayList<>();
        Cursor cursor = new DbManager(context).fetchHistory();
        while (cursor.moveToNext()) {
            GameDetails gameDetails = new GameDetails(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            allHistoryDetails.add(gameDetails);
        }
        mutableLiveData.setValue(allHistoryDetails);
    }

    public void initHistorySection() {
        mutableLiveData.setValue(new ArrayList<>());
    }

    public void addGameDetails(GameDetails gameDetails) {

//        if (mutableLiveData.getValue() == null) {
//            initHistorySection();
//        }
//        List<GameDetails> gamesPlayed = new ArrayList<>(mutableLiveData.getValue());
//        gamesPlayed.add(gameDetails);
//        mutableLiveData.setValue(gamesPlayed);
    }


    public void resettingHistorySection() {
        initHistorySection();
    }
}
