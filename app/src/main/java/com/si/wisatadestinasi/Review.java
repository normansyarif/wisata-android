package com.si.wisatadestinasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.si.wisatadestinasi.Database.Routes;

public class Review extends AppCompatActivity {
    private ShimmerFrameLayout shimmer;
    private WebView webview;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private Routes routes = new Routes();
    private Helper helper = new Helper();

    private String itemId, contentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ulasan");

        shimmer = findViewById(R.id.shimmer);
        webview = findViewById(R.id.webview);
        mainLayout = findViewById(R.id.main_layout);
        errorLayout = findViewById(R.id.error_layout);

        shimmer.startShimmerAnimation();
        helper.startLoading(shimmer, webview);

        Intent intent = getIntent();

        itemId = intent.getStringExtra("id");
        contentType = intent.getStringExtra("type");

        initWebview();
    }

    private void initWebview() {
        helper.startLoading(shimmer, webview);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
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
        webview.loadUrl(routes.reviews(contentType, itemId));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //----- fire when 'coba lagi' button is clicked ------//

    public void reloadWebview(View v) {
        helper.hideError(mainLayout, errorLayout);
        initWebview();
    }


    //------------ GUN, BUAT NI NGELINK KE POST RATING ---------------//

    public void addReview(View v) {
        Intent intent = new Intent(Review.this, FormRating.class);
        intent.putExtra("id", itemId);
        intent.putExtra("type", contentType);
        startActivity(intent);
    }
}
