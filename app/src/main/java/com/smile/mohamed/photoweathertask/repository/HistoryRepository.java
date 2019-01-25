package com.smile.mohamed.photoweathertask.repository;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.Arrays;
import java.util.List;



public class HistoryRepository {
    public List<File> getHistory(Context context){
        File dir=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        List<File> files=Arrays.asList(dir.listFiles());
        return files;
    }
}
