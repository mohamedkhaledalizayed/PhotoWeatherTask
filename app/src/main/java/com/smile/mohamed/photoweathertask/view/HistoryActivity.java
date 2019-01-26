package com.smile.mohamed.photoweathertask.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.smile.mohamed.photoweathertask.R;
import com.smile.mohamed.photoweathertask.databinding.ActivityHistoryBinding;
import com.smile.mohamed.photoweathertask.databinding.ActivityHomeBinding;
import com.smile.mohamed.photoweathertask.view.dialog.FullScreenPictureDialog;
import com.smile.mohamed.photoweathertask.view.interfaces.IHistoryHandler;
import com.smile.mohamed.photoweathertask.viewmodel.AddPictureViewModel;
import com.smile.mohamed.photoweathertask.viewmodel.HistoryViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements IHistoryHandler {

    private PictureHistoryAdapter mAdapter;
    private List<File> historyList = new ArrayList<>();
    private ActivityHistoryBinding binding;
    private HistoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_history);

        mAdapter = new PictureHistoryAdapter(this,historyList);
        binding.historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.historyRecycler.setAdapter(mAdapter);

        viewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyList.addAll(viewModel.getHistoryPictures());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(String url) {
        FragmentManager fm = getSupportFragmentManager();
        FullScreenPictureDialog editNameDialogFragment = FullScreenPictureDialog.newInstance(url);
        editNameDialogFragment.show(fm, "Full Screen");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }
}
