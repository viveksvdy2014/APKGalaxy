package com.speedyapps.apkgalaxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

import static android.R.id.list;

public class apkgalaxydata extends AppCompatActivity{
    SwipeRefreshLayout mSwipeRefreshLayout;
    String regex1="<div class=\"post-thumbnail\">";
    String regex2="</div><!--/.entry-->";
    String titleregex1="title=\"";
    String titleregex2=">";
    String imageregex="src=\"";
    String imageregex2=" class=";
    String detailsregex="<p>";
    String detailsregex2="</p>";
    CustomListAdapter adapter;
    String urlregex="<a href=\"";
    String urlregex2="\"";
    String ImageURLlist="";
    int pagecall=7;
    String str="";
    TextView tv;
    ArrayList<String> title;
    ArrayList<String> urlArrayFinal;
    ImageView loading;
    Runnable run;
    ArrayList<String> detailsArray;
    Handler handler;
    String string1="" ;
    ArrayList<Drawable> imageCache;
    String titleArray = "";
    Thread t,t2;
    URL url;
    Button loadMore;
    String targetURL="";
    InputStream is = null;
    ProgressBar progress;
    int pagenumber=1;
    BufferedReader br = null;
    ListView list;
    WebContent apps,games,wallpapers,themes,icons,launchers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apkgalaxydata);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         list.removeFooterView(loadMore);
                                                         list.removeFooterView(progress);
                                                         startFunction();
                                                     }
                                                 });
        startFunction();
    }

    public void startFunction(){
        pagecall=7;
        pagenumber=1;
        str="";
        string1="";
        ImageURLlist="";
        titleArray="";
        Intent intent = getIntent();
        list = (ListView)findViewById(R.id.list);
        title = new ArrayList<>();
        detailsArray = new ArrayList<>();
        urlArrayFinal = new ArrayList<>();
        imageCache=new ArrayList<>();
        adapter = new CustomListAdapter(apkgalaxydata.this, new ArrayList<String>(), new ArrayList<Drawable>(),new ArrayList<String>(),new ArrayList<String>(),new String[2]);
        adapter.notifyDataSetChanged();
        loadMore = new Button(this);
        loadMore.setText("Load More..");
        progress = new ProgressBar(this);
        progress.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .sweepSpeed(1f)
                .build());
        list.addFooterView(progress);
        //list.addFooterView(loadMore);
        switch(intent.getStringExtra("topic")){
            case "apps":
                apps=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-apps/";
                fetchData(targetURL,apps,true);
                break;
            case "games":
                games=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-games/";
                fetchData(targetURL,games,true);
                break;
            case "wallpapers" :
                wallpapers=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-wallpapers/";
                fetchData(targetURL,wallpapers,true);
                break;
            case "themes":
                themes=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-themes/";
                fetchData(targetURL,themes,true);
                break;
            case "icons":
                icons=new WebContent();
                targetURL="http://apkgalaxy.com/category/icon-packs/";
                fetchData(targetURL,icons,true);
                break;
            case "launchers":
                launchers=new WebContent();
                targetURL="http://apkgalaxy.com/category/android-launchers/";
                fetchData(targetURL,launchers,true);
                break;
            default:
                Toast.makeText(this, "Invalid Click", Toast.LENGTH_SHORT).show();
        }

    }

    public void nextPage(WebContent webContent){
        pagenumber++;
        str="";
        string1="";
        ImageURLlist="";
        titleArray="";
        fetchData(targetURL+"/page/"+pagenumber+"/",webContent,false);
    }



    public void fetchData(final String targetUrl, final WebContent webContent, final boolean firsttime){
        handler = new Handler();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        //        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
                        updateUI(str,webContent,firsttime);
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
    public void updateUI(String content, final WebContent webContent, final boolean firsttime)
    {

        //Pattern for Getting image thumbnail
        Pattern pattern = Pattern.compile(Pattern.quote(regex1)+"(.*?)"+Pattern.quote(regex2));
        Matcher m = pattern.matcher(content);
        while(m.find()){
            string1+=m.group(1);
        }
        string1=string1.replace("&#8211;","-");
        string1=string1.replace("&#038;","&");
        string1=string1.replace("&#46;",".");

        //Pattern for getting Titles
        Pattern pattern1 = Pattern.compile(Pattern.quote(titleregex1)+"(.*?)"+Pattern.quote(titleregex2));
        Matcher m2= pattern1.matcher(string1);
        int i=0;
        while(m2.find()){
            titleArray+=m2.group(1);
        }
        //Toast.makeText(this, ""+titleArray, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+titleArray, Toast.LENGTH_SHORT).show();
        final String[] titleCharArray = titleArray.split("\"");


        //1
        for(int k=1;k<titleCharArray.length;k+=2) {
            title.add(titleCharArray[k]);
        }
        webContent.setTitleArray(title);


        //Pattern for getting details from main page
        Pattern pattern3 = Pattern.compile(Pattern.quote(detailsregex)+"(.*?)"+Pattern.quote(detailsregex2));
        Matcher m4 = pattern3.matcher(string1);
        int p=0;
        while(m4.find()){
            detailsArray.add(m4.group(1));
            p++;
        }
        webContent.setSmallDescription(detailsArray);



//For Getting URL

        Pattern pattern4 = Pattern.compile(Pattern.quote(urlregex)+"(.*?)"+Pattern.quote(urlregex2));
        Matcher m5 = pattern4.matcher(string1);
        final String[] urlArray = new String[100];


        p=0;
        while(m5.find()){
            urlArray[p]=(m5.group(1));
            p++;
        }
        for(int f=0,d=-3;f<urlArray.length&&(d+3)<urlArray.length;f++)
            urlArrayFinal.add(urlArray[d+=3]);
        webContent.setUrlArray(urlArrayFinal);



//For getting Image
        final String[] ImageArray;
        Pattern pattern2 = Pattern.compile(Pattern.quote(imageregex)+"(.*?)"+Pattern.quote(imageregex2));
        Matcher m3= pattern2.matcher(string1);
        while(m3.find()){
            ImageURLlist+=m3.group(1);
        }

        ImageArray=ImageURLlist.split("\"");


             t = new Thread(new Runnable() {
                @Override
                public void run() {


                    for (int o = 0; o < ImageArray.length; o++) {
//2
                        imageCache.add(LoadImageFromUrlWeb(ImageArray[o]));
                        webContent.setImageArray(imageCache);
                        final int finalO = o;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadMore.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pagecall+=8;
                                        list.removeFooterView(loadMore);
                                        list.addFooterView(progress);
                                        nextPage(webContent);
                                    }
                                });
                                if(webContent.getImageArray()!=null&& finalO ==0&&firsttime==true) {
                                    adapter = new CustomListAdapter(apkgalaxydata.this, webContent.getTitleArray(), imageCache,webContent.getUrlArray(),webContent.getSmallDescription(),ImageArray);
                                    list.setAdapter(adapter);
                                }
                                else {
                                    list.invalidateViews();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.removeFooterView(progress);
                            list.addFooterView(loadMore);
                            mSwipeRefreshLayout.setRefreshing(false);


                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.removeCallbacks(run);
                           // loading.setVisibility(View.INVISIBLE);
                            //list.setEnabled(true);
                            //list.canScrollVertically(1);
                           // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        super.onBackPressed();
    }
}

