package com.smile.mohamed.photoweathertask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.smile.mohamed.photoweathertask.repository.HistoryRepository;
import java.io.File;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private List<File> responseLiveData;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        responseLiveData =new HistoryRepository().getHistory(application);
    }

    public List<File> getHistoryPictures(){
        return responseLiveData;
    }
}
