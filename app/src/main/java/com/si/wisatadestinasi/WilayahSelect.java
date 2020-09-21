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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.si.wisatadestinasi.Database.Routes;

public class WilayahSelect extends AppCompatActivity {
    private ShimmerFrameLayout shimmer;
    private WebView webview;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private Routes routes = new Routes();
    private Helper helper = new Helper();

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah_select);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shimmer = findViewById(R.id.shimmer);
        webview = findViewById(R.id.webview);
        mainLayout = findViewById(R.id.main_layout);
        errorLayout = findViewById(R.id.error_layout);

        shimmer.startShimmerAnimation();
        helper.startLoading(shimmer, webview);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        initWebview();

    }

    private void initWebview() {
        helper.startLoading(shimmer, webview);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, final String url, String message, JsResult result) {

                String mType = message.split(";")[0];
                String mId = message.split(";")[1];

                switch (mType) {
                    case "event-index":
                        showEventIndex(mId);
                        break;
                    case "event-detail":
                        showEventDetail(mId);
                        break;
                    default:
                        Toast.makeText(WilayahSelect.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        break;
                }

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
                //helper.showError(mainLayout, errorLayout);
                Toast.makeText(WilayahSelect.this, "Koneksi Internet tidak stabil", Toast.LENGTH_LONG).show();
            }
        });

        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webview.setLongClickable(false);
        webview.loadUrl(routes.wilayahSelect(id));
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


    //------- Click events --------//
    public void onHomestayClicked(View v) {
        showHomestayIndex(id);
    }

    public void onGuideClicked(View v) {
        showGuideIndex(id);
    }

    public void onSouvenirClicked(View v) {
        showSouvenirIndex(id);
    }

    public void onArtClicked(View v) {
        showArtIndex(id);
    }

    public void onRMClicked(View v) {
        showRMIndex(id);
    }

    public void onTaniClicked(View v) {
        showTaniIndex(id);
    }

    public void onUMKMClicked(View v) {
        showUMKMIndex(id);
    }

    public void onKendaraanClicked(View v) {
        showKendaraanIndex(id);
    }

    public void onBumdesClicked(View v) {
        Intent intent = new Intent(WilayahSelect.this, MultiPurposeWebview.class);
        intent.putExtra("url", routes.bumdes(id));
        startActivity(intent);
    }

    public void onGovClicked(View v) {
        Intent intent = new Intent(WilayahSelect.this, MultiPurposeWebview.class);
        intent.putExtra("url", routes.gov(id));
        startActivity(intent);
    }

    private void openIntent(String wilayahId, String contentType) {
        Intent intent = new Intent(WilayahSelect.this, ListItem.class);
        intent.putExtra("wilayahId", wilayahId);
        intent.putExtra("contentType", contentType);
        startActivity(intent);
    }

    private void showHomestayIndex(String wilayahId) {
        openIntent(wilayahId, "homestay");
    }

    private void showGuideIndex(String wilayahId) {
        openIntent(wilayahId, "guide");
    }

    private void showSouvenirIndex(String wilayahId) {
        openIntent(wilayahId, "souvenir");
    }

    private void showArtIndex(String wilayahId) {
        openIntent(wilayahId, "art");;
    }

    private void showRMIndex(String wilayahId) {
        openIntent(wilayahId, "rm");;
    }

    private void showTaniIndex(String wilayahId) {
        openIntent(wilayahId, "tani");;
    }

    private void showUMKMIndex(String wilayahId) {
        openIntent(wilayahId, "umkm");;
    }

    private void showKendaraanIndex(String wilayahId) {
        openIntent(wilayahId, "kendaraan");;
    }

    private void showEventIndex(String wilayahId) {
        openIntent(wilayahId, "event");
    }


    //--------- GUN, UBAH 2 METHOD DIBAWAH NI NGELINK KE INTENT DETAIL -------------------------//
    //--------- ID NYA MASING2 UDAH ADA DIBAWAH ------------------------------------------------//


    private void showEventDetail(String eventId) {
        Intent intent = new Intent(WilayahSelect.this, DetailEvent.class);
        intent.putExtra("id", eventId);
        startActivity(intent);
    }


}
