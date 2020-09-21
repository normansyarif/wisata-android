package com.si.wisatadestinasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.si.wisatadestinasi.Database.Routes;

import org.json.JSONException;
import org.json.JSONObject;

public class WilayahMap extends AppCompatActivity {
    private String WEBVIEW_URL;
    private WebView webview;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private ProgressBar progressBar;
    private Routes routes = new Routes();
    private Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_serbaguna);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webview = findViewById(R.id.webview);
        mainLayout = findViewById(R.id.main_layout);
        errorLayout = findViewById(R.id.error_layout);
        progressBar = findViewById(R.id.progress_bar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String type = extras.getString("type");
            String id_wilayah = extras.getString("id_wilayah");

            WEBVIEW_URL = routes.wilayahContent(id_wilayah, type);
        } else WEBVIEW_URL = routes.wilayahMap();

        progressBar.setVisibility(View.VISIBLE);
        initWebview();
    }

    private void initWebview() {
        progressBar.setVisibility(View.VISIBLE);
        webview.setVisibility(View.GONE);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, final String url, String message, JsResult result) {
                try {
                    JSONObject obj = new JSONObject(message);
                    String type = obj.getString("type");

                    if(type.equals("more")){
                        Intent intent = new Intent(WilayahMap.this, WilayahSelect.class);
                        intent.putExtra("id", obj.getString("id"));
                        startActivity(intent);
                    } else if(type.equals("nav")) {
                        Uri gmmIntentUri = Uri.parse(obj.getString("url"));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    } else {
                        Intent intent;
                        switch (type) {
                            case "event":
                                intent = new Intent(WilayahMap.this, DetailEvent.class);
                                break;
                            case "art":
                                intent = new Intent(WilayahMap.this, DetailArt.class);
                                break;
                            case "souvenir":
                                intent = new Intent(WilayahMap.this, DetailSouvenir.class);
                                break;
                            case "rm":
                                intent = new Intent(WilayahMap.this, DetailRM.class);
                                break;
                            case "tani":
                                intent = new Intent(WilayahMap.this, DetailTani.class);
                                break;
                            case "umkm":
                                intent = new Intent(WilayahMap.this, DetailUMKM.class);
                                break;
                            case "kendaraan":
                                intent = new Intent(WilayahMap.this, DetailKendaraan.class);
                                break;
                            default:
                                intent = new Intent(WilayahMap.this, DetailHomestay.class);
                                break;
                        }

                        intent.putExtra("id", obj.getString("id"));
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                result.cancel();
                return true;
            }
        });

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
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
        webview.loadUrl(WEBVIEW_URL);
    }

    public void reloadWebview(View v) {
        helper.hideError(mainLayout, errorLayout);
        initWebview();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
