package com.example.bt_android_thuctap.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PhotoAdapter extends  RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{

    private Context mContext;
    private List<Uri> mListPhoto;

    public PhotoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull

    public void setData(List<Uri> list){
        this.mListPhoto = list;
        notifyDataSetChanged();
    }

    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Uri uri = mListPhoto.get(position);
        if(uri == null ){
            return;
        }
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
            if(bitmap!= null){
                holder.imageView.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if(mListPhoto != null){
            mListPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePhoto);
        }

    }
}
