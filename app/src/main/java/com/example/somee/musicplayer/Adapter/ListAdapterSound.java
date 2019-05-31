package com.example.somee.musicplayer.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.somee.musicplayer.R;

import java.util.HashMap;
import java.util.List;

public class ListAdapterSound  extends BaseAdapter {


    private List<SoundModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public ListAdapterSound(List<SoundModel> listData, Context aContext) {
        this.listData = listData;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
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
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);

            holder = new ViewHolder();
            holder.FlagView = (ImageView) convertView.findViewById(R.id.imageView_flag);
            holder.NameView = (TextView) convertView.findViewById(R.id.textView_Name);
            holder.Desccrption = (TextView) convertView.findViewById(R.id.textView_descrition);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SoundModel soundModel =this.listData.get(position);
        holder.NameView.setText(soundModel.getMusic_name());
        holder.Desccrption.setText(soundModel.getMusic_description());
//        int imageId = this.getMipmapResIdByName(soundModel.getMusic_name());
//        holder.FlagView.setImageResource(imageId);
//        holder.FlagView.setImageDrawable(context.getDrawable(R.drawable.girlkid));
        loadimage(soundModel.getMusic_name(),holder.FlagView);
        return convertView;
    }
//    public int getMipmapResIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//        // Return 0 if not found.
//        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
//        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
//        return resID;
//    }

    static class ViewHolder {
        ImageView FlagView;
        TextView NameView;
        TextView Desccrption;

    }
    public void loadimage(String name,ImageView imageView){

        try {
            String name_temp=String.format("%s.mp3",name);
            String url = String.format("http://10.62.20.89/downloadFile/%s",name_temp);
            final MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(url, new HashMap<String, String>());
            try {
                final byte[] art = metaRetriever.getEmbeddedPicture();

                imageView.setImageBitmap(BitmapFactory.decodeByteArray(art, 0, art.length));
            } catch (Exception e) {

                imageView.setImageResource(R.drawable.nath);
            }


        } catch (NullPointerException e) {

            imageView.setImageResource(R.drawable.nath);
        }
    }
}
