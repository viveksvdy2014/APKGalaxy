package com.speedyapps.apkgalaxy;

import android.graphics.drawable.Drawable;

public class WebContent {

    private static String[] titleArray;
    private static Drawable[] imageArray;
    private static String[] urlArray;
    private static String description;
    private static String rating;
    private static String requirement;
    private static String[] downloadLinkArray;
    private static String[] smallDescription;


    WebContent(){

        this.titleArray=null;
        this.imageArray=null;
        this.urlArray=null;
        this.description=null;
        this.rating=null;
        this.requirement=null;
        this.downloadLinkArray=null;
        this.smallDescription=null;

    }

    public static String[] getTitleArray() {
        return titleArray;
    }

    public static Drawable[] getImageArray() {
        return imageArray;
    }

    public static String[] getUrlArray() {
        return urlArray;
    }

    public static String getDescription() {
        return description;
    }

    public static String getRating() {
        return rating;
    }

    public static String getRequirement() {
        return requirement;
    }

    public static String[] getDownloadLinkArray() {
        return downloadLinkArray;
    }

    public static String[] getSmallDescription() {
        return smallDescription;
    }

    public static void setTitleArray(String[] titleArray) {
        WebContent.titleArray = titleArray;
    }

    public static void setImageArray(Drawable[] imageArray) {
        WebContent.imageArray = imageArray;
    }

    public static void setUrlArray(String[] urlArray) {
        WebContent.urlArray = urlArray;
    }

    public static void setDescription(String description) {
        WebContent.description = description;
    }

    public static void setRating(String rating) {
        WebContent.rating = rating;
    }

    public static void setRequirement(String requirement) {
        WebContent.requirement = requirement;
    }

    public static void setDownloadLinkArray(String[] downloadLinkArray) {
        WebContent.downloadLinkArray = downloadLinkArray;
    }
    public static void setSmallDescription(String[] smallDescription) {
        WebContent.smallDescription = smallDescription;
    }
}
