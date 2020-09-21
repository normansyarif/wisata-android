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

public class DetailGuide extends AppCompatActivity {
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

    public Activity activity = DetailGuide.this;
    String id, foto, pluscode, email, wa, hp = null;
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_guide);

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
        api.getDataVolley(routes.getGuide + getIntent().getStringExtra("id"), activity, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    TextView tvWilayah,  tvTarif, tvRating, tvOrang;
                    String wilayah,  tarif, rating, orang;

                    tvWilayah = findViewById(R.id.wilayah);
                    tvRating = findViewById(R.id.rating);
                    tvOrang  = findViewById(R.id.orang);
                    tvTarif  = findViewById(R.id.tarif);
                    ivFoto = findViewById(R.id.header);

                    id        = result.getString("id");
                    foto      = result.getString("foto");
                    pluscode  = null;
                    email     = result.getString("email");
                    hp        = result.getString("hp");
                    wa        = result.getString("wa");

                    wilayah   = result.getString("wilayah");
                    rating    = result.getString("rating");
                    orang     = result.getString("orang");
                    tarif     = result.getString("tarif");

                    tvWilayah.setText(wilayah);
                    tvRating.setText(rating);
                    tvOrang.setText(orang);
                    tvTarif.setText(tarif);

                    helper.stopVolley(shimmer, parentDetail, menu);
                    collapsingToolbar.setTitle(result.getString("nama"));
                    ToolClass.menuFab(activity, pluscode, email, wa, hp);
                    ToolClass.btnListener(activity, "guide", id);
                    ToolClass.imgListener(activity, routes.gambarGuide + foto);

                    Glide.with(activity).load(routes.gambarGuide + foto).fitCenter().into(ivFoto);
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
