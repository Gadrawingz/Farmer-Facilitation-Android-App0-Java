package com.donnekt.farmerapp;

public class URLs {

    private static final String MAIN_URL = "https://apitumba.000webhostapp.com/farmerapp/";

    public static final String FARMER_REGISTER = MAIN_URL+"farmer_api?action=register";
    public static final String FARMER_LOGIN = MAIN_URL +"farmer_api?action=login";
    public static final String FARMER_UPDATE = MAIN_URL +"farmer_api?action=update&upd_farmer=";

    public static final String HARVEST_REGISTER = MAIN_URL +"farmer_api?action=reg_harvest";
    public static final String HARVEST_UPDATE = MAIN_URL +"farmer_api?action=update&upd_harvest=";
    public static final String HARVEST_VIEW_ALL = MAIN_URL +"farmer_api?action=view_harvest";
    public static final String HARVEST_VIEW_BY = MAIN_URL +"farmer_api?action=view&hv_farmer=";

    public static final String FARMER_REQUEST = MAIN_URL +"farmer_api?action=frequest";
    public static final String FARMER_VIEW_REQ = MAIN_URL +"farmer_api?action=view_request";

    public static final String SD_VIEW_ALL = MAIN_URL +"farmer_api?action=view_seeds";
    public static final String PS_VIEW_ALL = MAIN_URL +"farmer_api?action=view_pesticides";
    public static final String FR_VIEW_ALL = MAIN_URL +"farmer_api?action=view_fertilizers";


    public static final String FR_LIMITATION = MAIN_URL +"farmer_api?limitation&land_area=";
    public static final String FR_VIEW_REQUEST = MAIN_URL +"farmer_api?action=view&req_farmer=";
    public static final String FR_FINALIZATION = MAIN_URL +"farmer_api?action=kick_order";

}
