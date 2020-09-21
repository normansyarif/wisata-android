package com.si.wisatadestinasi.Database;

import com.android.volley.VolleyError;

public interface VolleyResponseListener {
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
}
