package com.speedyapps.apkgalaxy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsActivity extends AppCompatActivity {

    Thread t2;
    URL urll;
    String str="";
    InputStream is = null;
    BufferedReader br = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        try {
            updateUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI() throws IOException {




        Intent intent = getIntent();
        String url=null,description=null,title=null,image=null;
        Drawable drawable = null;
        final TextView titlet = (TextView)findViewById(R.id.titleDetails);
        final TextView descriptiont = (TextView)findViewById(R.id.description);
        TextView downloadsTitle = (TextView)findViewById(R.id.downloadTitle);
        final ImageView imageView = (ImageView)findViewById(R.id.imageView8) ;
        url=intent.getStringExtra("url");
        title=intent.getStringExtra("title");
        image=intent.getStringExtra("image");
        final String finalImage = image;
        final String finalTitle = title;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = (InputStream) new URL(finalImage).getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Drawable d = Drawable.createFromStream(is, "src name");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(d);
                        titlet.setText(finalTitle);
                    }
                });
            }
        });
        t.start();

        final String finalUrl = url;
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    urll = new URL(finalUrl);
                    is = urll.openStream();  // throws an IOException
                    br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                        str +=line;
                    }
                    //getDesciption(str);
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    try {
                        if (is != null) is.close();
                        br.close();
                    } catch (IOException ioe) {
                        // nothing to see here
                    }
                }
                try {
                    if (is != null) is.close();
                    br.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }
        });
        t2.start();





    }



    public static void getDesciption(String string){
        String startingRegex = "<div class=\"entry-inner\">";
        String endingRegex="<div class=\"sharrre-container\">";
        Pattern p = Pattern.compile(Pattern.quote(startingRegex)+"(.*?)"+Pattern.quote(endingRegex));


    }

}
