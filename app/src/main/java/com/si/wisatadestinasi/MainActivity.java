package com.si.wisatadestinasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.si.wisatadestinasi.Database.Routes;

import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {
    private ShimmerFrameLayout shimmer;
    private WebView webview;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private Routes routes = new Routes();
    private Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmer = findViewById(R.id.shimmer);
        webview = findViewById(R.id.webview);
        mainLayout = findViewById(R.id.main_layout);
        errorLayout = findViewById(R.id.error_layout);

        shimmer.startShimmerAnimation();
        helper.startLoading(shimmer, webview);

        initWebview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kontakAdmin:
                Intent intent = new Intent(MainActivity.this, KontakAdmin.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initWebview() {
        helper.startLoading(shimmer, webview);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, final String url, String message, JsResult result) {

                Intent intent = new Intent(MainActivity.this, WilayahSelect.class);
                intent.putExtra("id", message);
                startActivity(intent);

                result.cancel();
                return true;
            }
        });

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                helper.stopLoading(shimmer, webview);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                helper.showError(mainLayout, errorLayout);
            }
        });

        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webview.setLongClickable(false);
        webview.loadUrl(routes.wilayahIndex());
    }

    public void reloadWebview(View v) {
        helper.hideError(mainLayout, errorLayout);
        initWebview();
    }

    public void onMapClicked(View v) {
        Intent intent = new Intent(MainActivity.this, WilayahMap.class);
        startActivity(intent);
    }
}
