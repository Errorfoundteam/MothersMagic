package com.eft.mothersmagicc.model;

import java.util.ArrayList;

public class savedata {
    public static String shortlocation="no";
    public static String longlocation="no";
    public static String latitude="no";
    public static String longitude="no";
    public static ArrayList<getfoodlist> foodarray;
    public static int itemArrayClickPosition;

    public savedata(String lat, String lng, String srtAdd, String lngAdd){

        shortlocation=srtAdd;
        longlocation=lngAdd;
        latitude=lat;
        longitude=lng;
    }

    public static void fooditemdetail(ArrayList<getfoodlist> list) {
        foodarray=list;
    }
    public static void fooditemposition(int position) {
        itemArrayClickPosition=position;
    }
}
