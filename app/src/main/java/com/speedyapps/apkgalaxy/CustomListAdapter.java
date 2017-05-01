package com.speedyapps.apkgalaxy;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
        import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Drawable> imgid;
    private final ArrayList<String> urlList;
    private final ArrayList<String> description;
    private final String[] ImageArray;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Drawable> imgid,ArrayList<String> urlList,ArrayList<String> description,String[] ImageArray) {
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.urlList=urlList;
        this.description=description;
        this.ImageArray=ImageArray;
    }

    @Override
    public int getCount() {
        return imgid.size();
    }
    @Override
    public Object getItem(int position) {
        return itemname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ImageView imageView = null;
        LayoutInflater inflater=context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.mylist, null);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.textView3);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView2);
            txtTitle.setText(itemname.get(position));
            imageView = (ImageView) rowView.findViewById(R.id.icon);
            imageView.setImageDrawable(imgid.get(position));
            extratxt.setText(description.get(position));
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailsActivity.class);
                    intent.putExtra("url",urlList.get(position));
                    intent.putExtra("image",""+imgid.get(position));
                    intent.putExtra("title",itemname.get(position));
                    intent.putExtra("description",description.get(position));
                    context.startActivity(intent);
                }
            });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("url",urlList.get(position));
                BitmapDrawable bitDw = ((BitmapDrawable) imgid.get(position));
                Bitmap bitmap = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                intent.putExtra("image",ImageArray[position]);
                intent.putExtra("title",itemname.get(position));
                intent.putExtra("description",description.get(position));
                context.startActivity(intent);
            }
        });

        return rowView;




    }
    }