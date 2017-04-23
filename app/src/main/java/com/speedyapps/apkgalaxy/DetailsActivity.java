package com.speedyapps.apkgalaxy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    TextView titlet,descriptiont;
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
        titlet = (TextView)findViewById(R.id.titleDetails);
        descriptiont = (TextView)findViewById(R.id.description);
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
                    getDesciption(str);
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
    public void getDesciption(String string){

        final String startingRegex = "<p>";
        String endingRegex="</p>";
        String regex2start="<div><a href=\"";
        String regex2end="</a></div>";
        Pattern p = Pattern.compile(Pattern.quote(startingRegex)+"(.*?)"+Pattern.quote(endingRegex));
        Matcher matcher = p.matcher(string);
        String posts="";
        while(matcher.find()){
            posts+=matcher.group(1)+"\n\n";
        }
        posts=posts.replace("&#8211;","-");
        posts=posts.replace("&#8217;","'");
        posts=posts.replace("&#038;","&");
        posts=posts.replace("<br />","\n");
        posts=posts.replace("-&gt;","->");

        String description = "";
        description=posts.split("<h5>")[0];
        final String finalDescription = description;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                descriptiont.setText(finalDescription);
            }
        });

        String getDownloadURL = posts.split("<h5>")[1];
        Pattern p2 = Pattern.compile(Pattern.quote(regex2start)+"(.*?)"+Pattern.quote(regex2end));
        Matcher matcher2 = p2.matcher(getDownloadURL);
        String[] posts2=new String[10];
        int q=0;
        while(matcher2.find()){
            posts2[q++]=matcher2.group(1);
        }
        String driveURL="";
        for(q=0;q<2;q++){
            if(posts2[q].contains("Google Drive")){
                driveURL = posts2[q].split("\"")[0];
            }
        }
        final String finalDriveURL = driveURL;
        final String finalDriveURL1 = driveURL;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView downloadImage = (ImageView)findViewById(R.id.driveImage);
                downloadImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailsActivity.this,downloadLinkBrowser.class);
                        intent.putExtra("url", finalDriveURL1);
                        startActivity(intent);
                    }
                });

            }
        });
        Log.i("zz",""+posts2);
//        Toast.makeText(DetailsActivity.this, ""+posts, Toast.LENGTH_SHORT).show();
    }

}
