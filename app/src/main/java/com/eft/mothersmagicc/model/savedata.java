package com.eft.mothersmagicc.model;

public class savedata {
    public static String shortlocation="no";
    public static String longlocation="no";
    public static String latitude="no";
    public static String longitude="no";
    public savedata(String lat, String lng, String srtAdd, String lngAdd){

        shortlocation=srtAdd;
        longlocation=lngAdd;
        latitude=lat;
        longitude=lng;
    }
}
