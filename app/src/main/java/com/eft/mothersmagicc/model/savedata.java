package com.eft.mothersmagicc.model;

import java.util.ArrayList;

public class savedata {

    public static String shortlocation="no";
    public static String longlocation="no";
    public static String latitude="no";
    public static String longitude="no";
    public static ArrayList<getfoodlist> foodarray;
    public static int itemArrayClickPosition;
    public static String currect_address;
    public static int totalFoodPrice;
    public static int foodQuantity;
    public static String userPhoneNumber="";

    public savedata(String lat, String lng, String srtAdd, String lngAdd){

        shortlocation=srtAdd;
        longlocation=lngAdd;
        latitude=lat;
        longitude=lng;
    }
public static void setCurrect_address(String add){
        currect_address=add;
}
    public static void fooditemdetail(ArrayList<getfoodlist> list) {
        foodarray=list;
    }
    public static void fooditemposition(int position) {
        itemArrayClickPosition=position;
    }
    public static void setTotalPriceNQuantity(int q,int totalprice) {
        foodQuantity=q;
        totalFoodPrice=totalprice;
    }
    public static void setUserPh(String ph) {
    userPhoneNumber=ph;
    }

}
