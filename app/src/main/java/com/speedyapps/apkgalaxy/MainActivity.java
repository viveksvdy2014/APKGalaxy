package com.speedyapps.apkgalaxy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    
    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this,apkgalaxydata.class);
        switch(view.getId()){
            
            case R.id.apps:
                intent.putExtra("topic","apps");
                break;
            case R.id.games:
                intent.putExtra("topic","games");
                break;
            case R.id.wallpapers:
                intent.putExtra("topic","wallpapers");
                break;
            case R.id.themes:
                intent.putExtra("topic","themes");
                break;
            case R.id.iconpacks:
                intent.putExtra("topic","icons");
                break;
            case R.id.launchers:
                intent.putExtra("topic","launchers");
                break;
        }
        startActivity(intent);
    }
}
