package com.wj.xpullze.com.wj.xpullze.utils.com.wj.xpullze.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wj.xpullze.R;
import com.wj.xpullze.com.wj.xpullze.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @version 1.0
 * @date 2017/7/7
 */

public class GridPicListAdapter extends BaseAdapter {
    private List<Bitmap> data;
    private Context mContext;
    public GridPicListAdapter(Context cxt, ArrayList<Bitmap> data){
        mContext = cxt;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        int density = (int) ScreenUtils.getDeviceDensity(mContext);
        if(convertView==null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(80*density,100*density));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }else{
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundColor(Color.BLACK);
        imageView.setImageBitmap(data.get(position));
        return imageView;
    }
}
