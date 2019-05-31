package com.example.somee.musicplayer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.somee.musicplayer.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter_list_item_home extends BaseAdapter {
    private List<commentmodel> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapter_list_item_home(List<commentmodel> listData, Context context) {
        this.listData = listData;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
         convertView =layoutInflater.inflate(R.layout.list_item_home, null);
         holder =new ViewHolder();
         holder.username =(TextView)convertView.findViewById(R.id.username);
         holder.comment =(TextView)convertView.findViewById(R.id.comment);
         holder.userpic =(CircleImageView)convertView.findViewById(R.id.userpic);
         convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.comment.setText( listData.get(position).getComment_text());
        holder.username.setText( listData.get(position).getUser_fname());

        return convertView;
    }
    static class ViewHolder {
        TextView username;
        TextView comment;
        CircleImageView userpic;

    }
}
