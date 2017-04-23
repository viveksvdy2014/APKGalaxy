package com.speedyapps.apkgalaxy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class downloadLinkBrowser extends AppCompatActivity {
    URL urll;
    InputStream is;
    BufferedReader br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_link_browser);
        String weburl="";
        Intent intent = getIntent();
        weburl=intent.getStringExtra("url");
        final String finalWeburl = weburl;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    urll = new URL(finalWeburl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    is = urll.openStream();  // throws an IOException
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String line="",str="";
                br = new BufferedReader(new InputStreamReader(is));
                try {
                    while ((line = br.readLine()) != null) {
                        str +=line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String regex1="onclick=\"window.open('";
                String regex2="', '_parent');\">";

                Pattern p2 = Pattern.compile(Pattern.quote(regex1)+"(.*?)"+Pattern.quote(regex2));
                Matcher matcher2 = p2.matcher(str);
                final String[] posts2=new String[10];
                int q=0;
                while(matcher2.find()){
                    posts2[q++]=matcher2.group(1);
                }
                final String finalStr = str;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(downloadLinkBrowser.this, ""+posts2[0], Toast.LENGTH_SHORT).show();
                        Log.i("zz",""+posts2[0]);
                        WebView myWebView = (WebView) findViewById(R.id.webView);
                        myWebView.loadUrl(posts2[0]);
                    }
                });




            }
        }) ;
        thread.start();

    }
}
