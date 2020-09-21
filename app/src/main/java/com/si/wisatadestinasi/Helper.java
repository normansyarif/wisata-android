package com.si.wisatadestinasi;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionMenu;

class Helper {

    void startLoading(ShimmerFrameLayout shimmer, WebView webview) {
        shimmer.setVisibility(View.VISIBLE);
        webview.setVisibility(View.GONE);
    }

    void stopLoading(ShimmerFrameLayout shimmer, WebView webview) {
        shimmer.setVisibility(View.GONE);
        webview.setVisibility(View.VISIBLE);
    }

    void startVolley(ShimmerFrameLayout shimmer, CoordinatorLayout parentDetail, FloatingActionMenu menu) {
        shimmer.setVisibility(View.VISIBLE);
        parentDetail.setVisibility(View.GONE);
        if(menu != null) menu.setVisibility(View.GONE);
    }

    void stopVolley(ShimmerFrameLayout shimmer, CoordinatorLayout parentDetail, FloatingActionMenu menu) {
        shimmer.setVisibility(View.GONE);
        parentDetail.setVisibility(View.VISIBLE);
        if(menu != null) menu.setVisibility(View.VISIBLE);
    }

    void showError(LinearLayout mainLayout, LinearLayout errorLayout) {
        mainLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    void hideError(LinearLayout mainLayout, LinearLayout errorLayout) {
        mainLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }
}
