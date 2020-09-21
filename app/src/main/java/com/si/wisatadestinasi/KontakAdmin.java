package com.si.wisatadestinasi;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.si.wisatadestinasi.Database.Routes;
import com.si.wisatadestinasi.Database.VolleyAPI;
import com.si.wisatadestinasi.Database.VolleyResponseListener;

import java.util.HashMap;
import java.util.Map;

public class KontakAdmin extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    public Context context = KontakAdmin.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_admin);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final LinearLayout thanks = findViewById(R.id.thanks);
        final LinearLayout form = findViewById(R.id.form);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText eNama, eEmail, eJudul, ePesan;
                String nama, email, judul, pesan = "";

                eNama = findViewById(R.id.nama);
                eEmail = findViewById(R.id.email);
                eJudul = findViewById(R.id.judul);
                ePesan = findViewById(R.id.pesan);

                nama = eNama.getText().toString();
                email = eEmail.getText().toString();
                judul = eJudul.getText().toString();
                pesan = ePesan.getText().toString();

                if(nama.equals("") || email.equals("") || judul.equals("") || pesan.equals("")){
                    Toast.makeText(context, "Harap Lengkapi Form", Toast.LENGTH_LONG).show();
                } else {
                    form.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    Map<String, String> data = new HashMap<>();
                    data.put("nama", nama);
                    data.put("email", email);
                    data.put("judul", judul);
                    data.put("pesan", pesan);

                    api.postDataVolley(data, routes.kontakAdmin, context, new VolleyResponseListener() {

                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                progressBar.setVisibility(View.GONE);
                                thanks.setVisibility(View.VISIBLE);
                            } else {
                                form.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                api.handleError(response, context);
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            form.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            api.handleError(error.toString(), context);
                        }

                    });
                }

            }
        });
    }
}
