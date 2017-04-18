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

public class CustomListAdapter extends BaseAdapter {

    private final Activity context;
    private final String[] itemname;
    private final Drawable[] imgid;
    private final String[] urlList;
    private final String[] description;
    private final String[] ImageArray;

    public CustomListAdapter(Activity context, String[] itemname, Drawable[] imgid,String[] urlList,String[] description,String[] ImageArray) {
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
        return itemname.length;
    }

    @Override
    public Object getItem(int position) {
        return itemname[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.mylist, null);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.textView3);
            final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView2);
            txtTitle.setText(itemname[position]);
            imageView.setImageDrawable(imgid[position]);
            extratxt.setText(description[position]);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailsActivity.class);
                    intent.putExtra("url",urlList[position]);
                    intent.putExtra("image",imgid[position].toString());
                    intent.putExtra("title",itemname[position]);
                    intent.putExtra("description",description[position]);
                    context.startActivity(intent);
                }
            });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("url",urlList[position]);
                BitmapDrawable bitDw = ((BitmapDrawable) imgid[position]);
                Bitmap bitmap = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                intent.putExtra("image",ImageArray[position]);
                intent.putExtra("title",itemname[position]);
                intent.putExtra("description",description[position]);
                context.startActivity(intent);
            }
        });

        return rowView;




    }
    }