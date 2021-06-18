package com.alibi.triviaapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alibi.triviaapp.model.GameDetails;
import com.alibi.triviaapp.repositories.HistoryRepo;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {


    private Context context;
    HistoryRepo historyRepo = new HistoryRepo();

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }

    public LiveData<List<GameDetails>> getHistory() {
        return historyRepo.getHistoryData(context);
    }


    public void addGameDetailsToHistoryRepo(GameDetails gameDetails) {
        historyRepo.addGameDetails(gameDetails);
    }

    public void reSetHistorySection() {
        historyRepo.resettingHistorySection();
    }
}
