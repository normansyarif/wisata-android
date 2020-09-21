package com.si.wisatadestinasi.Database;

public class Routes {
    public String getDestinasi, getEvent, getHomestay, getArt, getSouvenir, getGuide, getRM, getTani, getUMKM, getKendaraan;
    public String gambarDestinasi, gambarHomestay, gambarEvent, gambarSouvenir, gambarArt, gambarGuide, gambarRM, gambarTani, gambarUMKM, gambarKendaraan;
    public String base_URL, api_URL, webview_URL, rating, kontakAdmin;

    public Routes(){
        this.base_URL = "http://wisata.unja.ac.id";
        this.base_URL = "http://192.168.100.235:8000";

        this.api_URL  = this.base_URL + "/api";
        this.rating    = api_URL + "/rating";
        this.kontakAdmin  = api_URL + "/kontakAdmin";

        this.getDestinasi = api_URL + "/getDestinasi/";
        this.getHomestay  = api_URL + "/getHomestay/";
        this.getSouvenir  = api_URL + "/getSouvenir/";
        this.getEvent = api_URL + "/getEvent/";
        this.getArt   = api_URL + "/getArt/";
        this.getGuide = api_URL + "/getGuide/";
        this.getRM = api_URL + "/getRM/";
        this.getTani = api_URL + "/getTani/";
        this.getUMKM = api_URL + "/getUMKM/";
        this.getKendaraan = api_URL + "/getKendaraan/";

        this.gambarDestinasi = api_URL +"/../uploads/destinasi/";
        this.gambarHomestay  = api_URL +"/../uploads/homestay/";
        this.gambarEvent = api_URL +"/../uploads/event/";
        this.gambarSouvenir = api_URL +"/../uploads/souvenir/";
        this.gambarArt   = api_URL +"/../uploads/art/";
        this.gambarGuide = api_URL +"/../uploads/profile/";
        this.gambarRM  = api_URL +"/../uploads/rm/";
        this.gambarTani  = api_URL +"/../uploads/tani/";
        this.gambarUMKM  = api_URL +"/../uploads/umkm/";
        this.gambarKendaraan  = api_URL +"/../uploads/kendaraan/";

        this.webview_URL  = this.base_URL + "/webview";
    }



    //-------- Routes for webview ----------//

    public String wilayahIndex() {
        return webview_URL + "/wilayah-index";
    }

    public String wilayahMap() {
        return webview_URL + "/wilayah-map";
    }

    public String gov(String id) {
        return webview_URL + "/wv/gov/" + id;
    }

    public String bumdes(String id) {
        return webview_URL + "/wv/bumdes/" + id;
    }

    public String wilayahSelect(String id) {
        return webview_URL + "/wilayah-select?id=" + id;
    }

    public String deskripsi(String type, String id){
        return  webview_URL + "/deskripsi/" + type + "/" + id;
    }

    public String wilayahContent(String id, String type) {
        return webview_URL + "/wilayah-content/" + type + "/" + id;
    }
    /**
     * Get the content of homestay, destinasi, event, souvenir, tour guide, kelompok seni
     * @param listType type of content - homestay, destinasi, event, souvenir, guide, art.
     * @param wilayahId ya wilayah id lah, emg apalagi?.
     */
    public String listItem(String listType, String wilayahId) {
        return webview_URL + "/list?type=" + listType + "&id=" + wilayahId;
    }

    /**
     * Get review based on
     * @param reviewType type of review - homestay, destinasi, event, souvenir, guide, art.
     * @param itemId review id.
     */
    public String reviews(String reviewType, String itemId) {
        return webview_URL + "/review?type=" + reviewType + "&id=" + itemId;
    }

}
