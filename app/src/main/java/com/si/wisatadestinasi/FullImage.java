package com.si.wisatadestinasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FullImage extends AppCompatActivity {
    WebView myBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        myBrowser = findViewById(R.id.myBrowser);
        myBrowser.getSettings().setBuiltInZoomControls(true);
        myBrowser.getSettings().setDisplayZoomControls(false);
        myBrowser.getSettings().setLoadWithOverviewMode(true);
        myBrowser.getSettings().setUseWideViewPort(true);
        myBrowser.getSettings().setAppCacheEnabled(true);
        myBrowser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        myBrowser.loadUrl(getIntent().getStringExtra("url"));
    }
}
