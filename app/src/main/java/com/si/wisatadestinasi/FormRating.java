package com.si.wisatadestinasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.si.wisatadestinasi.Database.Preferences;
import com.si.wisatadestinasi.Database.Routes;
import com.si.wisatadestinasi.Database.VolleyAPI;
import com.si.wisatadestinasi.Database.VolleyResponseListener;

import java.util.HashMap;
import java.util.Map;

public class FormRating extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    public Context context = FormRating.this;

    Preferences prefUser;
    TextView tvNama, tvUlasan;
    String id, type, nama, ulasan, rating = "";

    ImageView star1, star2, star3, star4, star5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rating);

        Preferences.getInstance().Initialize(getApplicationContext());
        prefUser = Preferences.getInstance();
        tvNama = findViewById(R.id.nama);
        tvUlasan = findViewById(R.id.ulasan);
        tvNama.setText(prefUser.getNama());

        ratingAnimation();
        sendRating();
    }

    private void ratingAnimation(){
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "1";
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_grey);
                star3.setImageResource(R.drawable.ic_star_grey);
                star4.setImageResource(R.drawable.ic_star_grey);
                star5.setImageResource(R.drawable.ic_star_grey);
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "2";
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_grey);
                star4.setImageResource(R.drawable.ic_star_grey);
                star5.setImageResource(R.drawable.ic_star_grey);
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "3";
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                star4.setImageResource(R.drawable.ic_star_grey);
                star5.setImageResource(R.drawable.ic_star_grey);
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "4";
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                star4.setImageResource(R.drawable.ic_star_yellow);
                star5.setImageResource(R.drawable.ic_star_grey);
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "5";
                star1.setImageResource(R.drawable.ic_star_yellow);
                star2.setImageResource(R.drawable.ic_star_yellow);
                star3.setImageResource(R.drawable.ic_star_yellow);
                star4.setImageResource(R.drawable.ic_star_yellow);
                star5.setImageResource(R.drawable.ic_star_yellow);
            }
        });

    }

    private void sendRating(){
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button btnSend =findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = getIntent().getStringExtra("id");
                type = getIntent().getStringExtra("type");

                nama = tvNama.getText().toString();
                ulasan = tvUlasan.getText().toString();

                if(nama.equals("") || rating.equals("")) {
                    Toast.makeText(context, "Harap lengkapi form", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);

                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("type", type);
                data.put("nama", nama);
                data.put("ulasan", ulasan);
                data.put("rating", rating);

                api.postDataVolley(data, routes.rating, context, new VolleyResponseListener() {

                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            prefUser.writeUser("nama", nama);

                            finish();
                            Intent intent = new Intent(context, Review.class);
                            intent.putExtra("id", id);
                            intent.putExtra("type", type);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSend.setVisibility(View.VISIBLE);
                            api.handleError(response, context);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnSend.setVisibility(View.VISIBLE);
                        api.handleError(error.toString(), context);
                    }

                });
            }
        });
    }
}
