package com.si.wisatadestinasi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.si.wisatadestinasi.Database.Routes;
import com.si.wisatadestinasi.Database.VolleyAPI;
import com.si.wisatadestinasi.Database.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailEvent extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private Menu collapseMenu;

    private Helper helper = new Helper();
    private FloatingActionMenu menu;
    private ShimmerFrameLayout shimmer;
    private CoordinatorLayout parentDetail;

    public Activity activity = DetailEvent.this;
    String foto, latlng, email, wa, hp = null;
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        toolbar           = findViewById(R.id.anim_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout      = findViewById(R.id.appbar);

        parentDetail = findViewById(R.id.parentDetail);
        menu    = findViewById(R.id.menu);
        shimmer = findViewById(R.id.shimmer);
        shimmer.startShimmerAnimation();
        helper.startVolley(shimmer, parentDetail, menu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToolClass.designTemplate(activity, collapsingToolbar, appBarLayout);
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // ----------------------- SET DATA ------------------------------- //

    public void setData(){
        api.getDataVolley(routes.getEvent + getIntent().getStringExtra("id"), activity, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    TextView tvWilayah, tvLokasi, tvWaktu, tvTanggal;
                    String wilayah, lokasi, waktu, tanggal;

                    tvWilayah = findViewById(R.id.wilayah);
                    tvLokasi = findViewById(R.id.lokasi);
                    tvWaktu = findViewById(R.id.waktu);
                    tvTanggal = findViewById(R.id.tanggal);
                    ivFoto = findViewById(R.id.header);

                    foto      = result.getString("foto");
                    latlng    = result.getString("latlng");

                    wilayah   = result.getString("wilayah");
                    lokasi    = result.getString("lokasi");
                    waktu     = result.getString("waktu");
                    tanggal   = result.getString("tanggal");

                    tvWilayah.setText(wilayah);
                    tvLokasi.setText(lokasi);
                    tvTanggal.setText(tanggal);
                    tvWaktu.setText(waktu);

                    helper.stopVolley(shimmer, parentDetail, menu);
                    collapsingToolbar.setTitle(result.getString("nama"));
                    ToolClass.menuFab(activity, latlng, email, wa, hp);
                    ToolClass.imgListener(activity, routes.gambarEvent + foto);

                    Glide.with(activity).load(routes.gambarEvent + foto).fitCenter().into(ivFoto);
                } catch (JSONException e) {
                    e.printStackTrace();
                    api.handleError(e + response, activity);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), activity);
            }
        });
    }

}
