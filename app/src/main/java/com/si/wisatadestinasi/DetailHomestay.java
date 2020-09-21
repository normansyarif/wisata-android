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

public class DetailHomestay extends AppCompatActivity {
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

    public Activity activity = DetailHomestay.this;
    String id, foto, latlng, email, wa, hp = null;
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);

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
        api.getDataVolley(routes.getHomestay + getIntent().getStringExtra("id"), activity, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    TextView tvWilayah, tvFasilitas, tvKamar, tvHarga, tvAlamat, tvRating, tvOrang;
                    String wilayah, fasilitas, kamar, harga, alamat, rating, orang;

                    tvWilayah = findViewById(R.id.wilayah);
                    tvAlamat = findViewById(R.id.alamat);
                    tvRating = findViewById(R.id.rating);
                    tvOrang  = findViewById(R.id.orang);
                    tvKamar  = findViewById(R.id.kamar);
                    tvHarga  = findViewById(R.id.harga);
                    tvFasilitas  = findViewById(R.id.fasilitas);
                    ivFoto = findViewById(R.id.header);

                    id        = result.getString("id");
                    foto      = result.getString("foto");
                    latlng    = result.getString("latlng");
                    email     = result.getString("email");
                    hp        = result.getString("hp");
                    wa        = result.getString("wa");

                    wilayah   = result.getString("wilayah");
                    alamat    = result.getString("alamat");
                    rating    = result.getString("rating");
                    orang     = result.getString("orang");
                    kamar     = result.getString("kamar_tersedia");
                    harga     = result.getString("kisaran_harga");
                    fasilitas = result.getString("fasilitas");

                    tvWilayah.setText(wilayah);
                    tvAlamat.setText(alamat);
                    tvRating.setText(rating);
                    tvOrang.setText(orang);
                    tvKamar.setText(kamar);
                    tvHarga.setText(harga);
                    tvFasilitas.setText(fasilitas);

                    helper.stopVolley(shimmer, parentDetail, menu);
                    collapsingToolbar.setTitle(result.getString("nama"));
                    ToolClass.menuFab(activity, latlng, email, wa, hp);
                    ToolClass.btnListener(activity, "homestay", id);
                    ToolClass.imgListener(activity, routes.gambarHomestay + foto);

                    Glide.with(activity).load(routes.gambarHomestay + foto).fitCenter().into(ivFoto);
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
