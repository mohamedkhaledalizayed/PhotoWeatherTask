package com.smile.mohamed.photoweathertask.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smile.mohamed.photoweathertask.R;
import com.smile.mohamed.photoweathertask.databinding.HistoryItemBinding;
import com.smile.mohamed.photoweathertask.model.History;
import com.smile.mohamed.photoweathertask.view.interfaces.IHistoryHandler;

import java.io.File;
import java.util.List;


public class PictureHistoryAdapter extends RecyclerView.Adapter<PictureHistoryAdapter.MyViewHolder> {

    private List<File> fileList;
    private Context context;
    private LayoutInflater layoutInflater;
    private IHistoryHandler handler;
    public PictureHistoryAdapter(Context context, List<File> recentList) {
        this.fileList = recentList;
        this.context=context;
        handler = (IHistoryHandler) context ;
    }

    @Override
    public PictureHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        HistoryItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.history_item, parent, false);

        return new PictureHistoryAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final PictureHistoryAdapter.MyViewHolder holder, final int position) {
        holder.binding.setImage(new History(fileList.get(position).getAbsolutePath()));
        holder.binding.addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.onClick(fileList.get(position).getAbsolutePath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HistoryItemBinding binding;
        public MyViewHolder(HistoryItemBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }

}