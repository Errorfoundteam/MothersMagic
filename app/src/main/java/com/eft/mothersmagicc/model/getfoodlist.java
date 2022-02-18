package com.eft.mothersmagicc.model;

public class getfoodlist {
    private String availability;
    private String bestseller;
    private String category1;
    private String category2;
    private String category3;
    private String cookedBy;
    private String details;
    private String foodname;
    private String full_price;
    private String half_price;
    private String ingrediants;
    private String location;
    private String ratings;
    private String time;
    private String totalbuyers;
    private String trending;
    private String veg;
    private String priceperkg;


    private String image;
    public getfoodlist(){}
    public getfoodlist( String availability, String bestseller,String category1,                       String category2,                       String category3,                       String cookedBy,                       String details,                       String foodname,                       String full_price,                       String half_price,                       String ingrediants,                       String location,                       String ratings,                       String time,                       String totalbuyers,                       String trending, String veg,                       String priceperkg,String image){
       this.foodname=foodname;
        this.availability=availability;this.cookedBy=cookedBy;
        this.bestseller=bestseller;this.details=details;
        this.category1=category1;this.full_price=full_price;
        this.half_price=half_price;this.ingrediants=ingrediants;
        this.location=location;this.ratings=ratings;
        this.time=time;this.totalbuyers=totalbuyers;
        this.trending=trending;
        this.veg=veg;
        this.priceperkg=priceperkg;
        this.category2=category2;
        this.category3=category3;
this.image=image;
    }

    public String getAvailability() {
        return availability;
    }

    public String getBestseller() {
        return bestseller;
    }

    public String getCategory1() {
        return category1;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory3() {
        return category3;
    }

    public String getCookedBy() {
        return cookedBy;
    }

    public String getDetails() {
        return details;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getFull_price() {
        return full_price;
    }

    public String getHalf_price() {
        return half_price;
    }

    public String getIngrediants() {
        return ingrediants;
    }

    public String getLocation() {
        return location;
    }

    public String getRatings() {
        return ratings;
    }

    public String getTime() {
        return time;
    }

    public String getTotalbuyers() {
        return totalbuyers;
    }

    public String getTrending() {
        return trending;
    }

    public String getVeg() {
        return veg;
    }

    public String getPriceperkg() {
        return priceperkg;
    }

    public String getImage() {
        return image;
    }

}
