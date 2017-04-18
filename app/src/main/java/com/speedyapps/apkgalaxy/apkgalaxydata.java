package com.speedyapps.apkgalaxy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;

public class apkgalaxydata extends AppCompatActivity {

    String regex1="<div class=\"post-thumbnail\">";
    String regex2="</div><!--/.entry-->";
    String titleregex1="title=\"";
    String titleregex2=">";
    String imageregex="src=\"";
    String imageregex2=" class=";
    String detailsregex="<p>";
    String detailsregex2="</p>";
    String urlregex="<a href=\"";
    String urlregex2="\"";
    String str="";
    TextView tv;
    ImageView loading;
    Runnable run;
    Handler handler;
    Thread t,t2;
    URL url;
    InputStream is = null;
    BufferedReader br = null;
    ListView list;
    WebContent apps,games,wallpapers,themes,icons,launchers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apkgalaxydata);
        Intent intent = getIntent();
        list = (ListView)findViewById(R.id.list);
        loading=(ImageView)findViewById(R.id.loading);
        String targetURL="";
        switch(intent.getStringExtra("topic")){
            case "apps":
                apps=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-apps/";
                fetchData(targetURL,apps);
                break;
            case "games":
                games=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-games/";
                fetchData(targetURL,games);
                break;
            case "wallpapers" :
                wallpapers=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-wallpapers/";
                fetchData(targetURL,wallpapers);
                break;
            case "themes":
                themes=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-themes/";
                fetchData(targetURL,themes);
                break;
            case "icons":
                icons=new WebContent();
                targetURL="http://apkgalaxy.com/category/icon-packs/";
                fetchData(targetURL,icons);
                break;
            case "launchers":
                launchers=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-launchers/";
                fetchData(targetURL,launchers);
                break;
            default:
                Toast.makeText(this, "Invalid Click", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchData(final String targetUrl, final WebContent webContent){
        handler = new Handler();
        loading.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        run = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.setRotation(loading.getRotation()+5);
                    }
                });
                handler.postDelayed(this,50);
            }
        };
        handler.post(run);

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    url = new URL(targetUrl);
                    is = url.openStream();  // throws an IOException
                    br = new BufferedReader(new InputStreamReader(is));

                    while ((line = br.readLine()) != null) {
                        str+=line;
                    }
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(str,webContent);
                    }
                });
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
    public void updateUI(String content, final WebContent webContent)
    {

        //Pattern for Getting image thumbnail
        Pattern pattern = Pattern.compile(Pattern.quote(regex1)+"(.*?)"+Pattern.quote(regex2));
        Matcher m = pattern.matcher(content);
        String string1="" ;
        while(m.find()){
            string1+=m.group(1);
        }
        string1=string1.replace("&#8211;","-");
        string1=string1.replace("&#038;","&");
        string1=string1.replace("&#46;",".");

        //Pattern for getting Titles
        Pattern pattern1 = Pattern.compile(Pattern.quote(titleregex1)+"(.*?)"+Pattern.quote(titleregex2));
        Matcher m2= pattern1.matcher(string1);
        String titleArray = "";
        int i=0;
        while(m2.find()){
            titleArray+=m2.group(1);
        }
        //Toast.makeText(this, ""+titleArray, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+titleArray, Toast.LENGTH_SHORT).show();
        final String[] titleCharArray = titleArray.split("\"");
        i=0;
        final String[] title = new String[titleCharArray.length/2];
        //1
        for(int k=1;k<titleCharArray.length;k+=2) {
            title[i] = titleCharArray[k];
            i++;
        }
        webContent.setTitleArray(title);


        //Pattern for getting details from main page
        Pattern pattern3 = Pattern.compile(Pattern.quote(detailsregex)+"(.*?)"+Pattern.quote(detailsregex2));
        Matcher m4 = pattern3.matcher(string1);
        final String[] detailsArray = new String[100];
        int p=0;
        while(m4.find()){
            detailsArray[p]=m4.group(1);
            p++;
        }
        webContent.setSmallDescription(detailsArray);



//For Getting URL

        Pattern pattern4 = Pattern.compile(Pattern.quote(urlregex)+"(.*?)"+Pattern.quote(urlregex2));
        Matcher m5 = pattern4.matcher(string1);
        final String[] urlArray = new String[100];
        final String[] urlArrayFinal = new String[100];
        p=0;
        while(m5.find()){
            urlArray[p]=m5.group(1);
            p++;
        }
        for(int f=0,d=-3;f<urlArray.length&&(d+3)<urlArray.length;f++)
            urlArrayFinal[f]=urlArray[d+=3];
        webContent.setUrlArray(urlArrayFinal);



//For getting Image
        String ImageURLlist="";
        final String[] ImageArray;
        Pattern pattern2 = Pattern.compile(Pattern.quote(imageregex)+"(.*?)"+Pattern.quote(imageregex2));
        Matcher m3= pattern2.matcher(string1);
        while(m3.find()){
            ImageURLlist+=m3.group(1);
        }

        ImageArray=ImageURLlist.split("\"");
        final Drawable imageCache[]=new Drawable[100];
             t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int o = 0; o < ImageArray.length; o++) {
//2
                        imageCache[o] = LoadImageFromUrlWeb(ImageArray[o]);
                        webContent.setImageArray(imageCache);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(webContent.getTitleArray()!=null) {
                                    final CustomListAdapter adapter = new CustomListAdapter(apkgalaxydata.this, webContent.getTitleArray(), webContent.getImageArray(),webContent.getUrlArray(),webContent.getSmallDescription(),ImageArray);
                                    list.setAdapter(adapter);
                                }
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.removeCallbacks(run);
                            loading.setVisibility(View.INVISIBLE);
                            //list.setEnabled(true);
                            //list.canScrollVertically(1);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });
                }
            });
            t.start();

    }
    public void listFunction(View view){

    }
    private Drawable LoadImageFromUrlWeb(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }
        catch (Exception e)
        {
            Log.w("LoadImageFromUrlWeb",e.toString());
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(run);
        loading.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        super.onBackPressed();
    }
}

