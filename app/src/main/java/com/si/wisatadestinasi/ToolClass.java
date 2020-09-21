package com.si.wisatadestinasi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.palette.graphics.Palette;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.si.wisatadestinasi.Database.Routes;

public class ToolClass {

    //  -------------------------- Detail Page -----------------------------  //

    public static void initWebView(Activity activity, String type, String id){
        WebView webView;
        webView = activity.findViewById(R.id.webView);

        Routes routes = new Routes();
        webView.loadUrl(routes.deskripsi(type, id));
        webView.setWebViewClient(new WebViewClient());
    }

    public static void openWhatsapp(Activity activity, String contact){
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = activity.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(activity, "Whatsapp app is not installed on your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void menuFab(final Activity activity, final String latlng, final String email, final String wa, final String hp) {
        com.google.android.material.floatingactionbutton.FloatingActionButton mapsBtn;
        mapsBtn = activity.findViewById(R.id.maps);

        if (!latlng.equals("null")){
            mapsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("https://maps.google.com/?q=" + latlng);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(mapIntent);
                    }
                }
            });
        }else{
            mapsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Lokasi tidak tersedia", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(email != null) {
            FloatingActionButton emailFab, teleponFab, whatsappFab;
            emailFab = activity.findViewById(R.id.email);
            emailFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));
                    activity.startActivity(intent);
                }
            });

            whatsappFab = activity.findViewById(R.id.whatsapp);
            whatsappFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToolClass.openWhatsapp(activity, wa);
                }
            });

            teleponFab = activity.findViewById(R.id.nohp);
            teleponFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", hp, null));
                    activity.startActivity(intent);
                }
            });
        }
    }

    public static void btnListener(final Activity activity, final String type, final String id){
        Button btnBacaUlasan;
        btnBacaUlasan = activity.findViewById(R.id.btnBacaUlasan);
        btnBacaUlasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Review.class);
                intent.putExtra("type", type);
                intent.putExtra("id", id);
                activity.startActivity(intent);
            }
        });
    }

    public static void imgListener(final Activity activity, final String url){
        ImageView ivFoto = activity.findViewById(R.id.header);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FullImage.class);
                intent.putExtra("url", url);
                activity.startActivity(intent);
            }
        });
    }

    public static void designTemplate(final Activity activity, final CollapsingToolbarLayout collapsingToolbar, final AppBarLayout appBarLayout){
        final boolean[] appBarExpanded = {true};

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.landscape);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.design_default_color_primary);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.design_default_color_primary_dark);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    appBarExpanded[0] = false;
                }else{
                    appBarExpanded[0] = true;
                }
                activity.invalidateOptionsMenu();
            }
        });
        
    }
}
