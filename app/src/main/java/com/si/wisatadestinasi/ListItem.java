package com.si.wisatadestinasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

import java.util.List;
import java.util.Objects;

public class ListItem extends AppCompatActivity {
    private ShimmerFrameLayout shimmer;
    private WebView webview;
    private LinearLayout mainLayout;
    private LinearLayout errorLayout;
    private Routes routes = new Routes();
    private Helper helper = new Helper();

    private String wilayahId, contentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shimmer = findViewById(R.id.shimmer);
        webview = findViewById(R.id.webview);
        mainLayout = findViewById(R.id.main_layout);
        errorLayout = findViewById(R.id.error_layout);

        shimmer.startShimmerAnimation();
        helper.startLoading(shimmer, webview);

        Intent intent = getIntent();
        wilayahId = intent.getStringExtra("wilayahId");
        contentType = intent.getStringExtra("contentType");

        setTitlebar();
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

                if(!message.equals("end")) {
                    switch (contentType) {
                        case "event":
                            showDetailEvent(message);
                            break;
                        case "homestay":
                            showDetailHomestay(message);
                            break;
                        case "guide":
                            showDetailGuide(message);
                            break;
                        case "art":
                            showDetailArt(message);
                            break;
                        case "souvenir":
                            showDetailSouvenir(message);
                            break;
                        case "rm":
                            showDetailRM(message);
                            break;
                        case "tani":
                            showDetailTani(message);
                            break;
                        case "umkm":
                            showDetailUMKM(message);
                            break;
                        case "kendaraan":
                            showDetailKendaraan(message);
                            break;
                        default:
                            Toast.makeText(ListItem.this, "Unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
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
        webview.loadUrl(routes.listItem(contentType, wilayahId));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!contentType.equals("guide") && !contentType.equals("destinasi")) getMenuInflater().inflate(R.menu.menugotomap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        } else if (id == R.id.showmap) {
            Intent intent = new Intent(ListItem.this, WilayahMap.class);
            intent.putExtra("id_wilayah", wilayahId);
            intent.putExtra("type", contentType);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initWebview();
    }

    //----- fire when 'coba lagi' button is clicked ------//
    public void reloadWebview(View v) {
        helper.hideError(mainLayout, errorLayout);
        initWebview();
    }

    private void setTitlebar() {
        switch (contentType) {
            case "destinasi":
                getSupportActionBar().setTitle("Destinasi");
                break;
            case "event":
                getSupportActionBar().setTitle("Event");
                break;
            case "homestay":
                getSupportActionBar().setTitle("Homestay");
                break;
            case "guide":
                getSupportActionBar().setTitle("Tour Guide");
                break;
            case "art":
                getSupportActionBar().setTitle("Kelompok Seni");
                break;
            case "souvenir":
                getSupportActionBar().setTitle("Souvenir");
                break;
            case "rm":
                getSupportActionBar().setTitle("Rumah Makan");
                break;
            case "tani":
                getSupportActionBar().setTitle("Produk Tani");
                break;
            case "umkm":
                getSupportActionBar().setTitle("UMKM");
                break;
            case "kendaraan":
                getSupportActionBar().setTitle("Kendaraan Sewa");
                break;
            default:
                Toast.makeText(ListItem.this, "Unknown error", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    //--------- GUN, UBAH 6 METHOD DIBAWAH NI NGELINK KE INTENT DETAIL--------------//
    //--------- ID NYA MASING2 UDAH ADA DIBAWAH ------------------------------------//

    private void showDetailSouvenir(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailSouvenir.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailArt(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailArt.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailGuide(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailGuide.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailEvent(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailEvent.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailHomestay(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailHomestay.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailRM(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailRM.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailTani(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailTani.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailUMKM(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailUMKM.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }

    private void showDetailKendaraan(String detailId) {
        Intent intent = new Intent(ListItem.this, DetailKendaraan.class);
        intent.putExtra("id", detailId);
        startActivity(intent);
    }
}
