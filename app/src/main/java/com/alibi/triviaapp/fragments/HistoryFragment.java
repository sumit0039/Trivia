package com.alibi.triviaapp.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.alibi.triviaapp.R;
import com.alibi.triviaapp.adapter.HistoryGamesPlayedAdapter;
import com.alibi.triviaapp.databinding.FragmentHistoryBinding;
import com.alibi.triviaapp.localdatabase.DbManager;
import com.alibi.triviaapp.model.GameDetails;
import com.alibi.triviaapp.viewmodels.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {


    private FragmentHistoryBinding historyBinding;
    private NavController navController;
    private List<GameDetails> allGameDetails;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        historyBinding = FragmentHistoryBinding.inflate(inflater, container, false);
        return historyBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.requireView().setFocusableInTouchMode(true);
        this.requireView().requestFocus();
        this.requireView().setOnKeyListener((v, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        allGameDetails = new ArrayList<>();
        //fetchGameDetails();
        HistoryGamesPlayedAdapter historyGamesPlayedAdapter = new HistoryGamesPlayedAdapter();
        historyBinding.historyRecycler.setAdapter(historyGamesPlayedAdapter);
        historyBinding.historyRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        //historyGamesPlayedAdapter.submitList(allGameDetails);
        HistoryViewModel historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
        historyViewModel.getHistory().observe(getViewLifecycleOwner(), gameDetails -> {
            Log.d("Game", "onChanged: " + gameDetails);
            historyGamesPlayedAdapter.submitList(gameDetails);
        });
//        historyBinding.reset.setOnClickListener(v -> historyViewModel.reSetHistorySection());

        historyBinding.restartGame.setOnClickListener(v -> navController.navigate(R.id.action_historyFragment_to_nameFragment));
    }

    private void fetchGameDetails() {
        Cursor cursor = new DbManager(requireContext()).fetchHistory();
        while (cursor.moveToNext()) {
            GameDetails gameDetails = new GameDetails(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            allGameDetails.add(gameDetails);
        }
    }


}