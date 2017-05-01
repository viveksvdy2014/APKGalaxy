package com.speedyapps.apkgalaxy;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class WebContent {
    public static ArrayList<Drawable> getImageArray() {
        return imageArray;
    }

    public static void setImageArray(ArrayList<Drawable> imageArray) {
        WebContent.imageArray = imageArray;
    }

    public static ArrayList<String> getUrlArray() {
        return urlArray;
    }

    public static void setUrlArray(ArrayList<String> urlArray) {
        WebContent.urlArray = urlArray;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        WebContent.description = description;
    }

    public static String getRating() {
        return rating;
    }

    public static void setRating(String rating) {
        WebContent.rating = rating;
    }

    public static String getRequirement() {
        return requirement;
    }

    public static void setRequirement(String requirement) {
        WebContent.requirement = requirement;
    }

    public static ArrayList<String> getDownloadLinkArray() {
        return downloadLinkArray;
    }

    public static void setDownloadLinkArray(ArrayList<String> downloadLinkArray) {
        WebContent.downloadLinkArray = downloadLinkArray;
    }

    public static ArrayList<String> getSmallDescription() {
        return smallDescription;
    }

    public static void setSmallDescription(ArrayList<String> smallDescription) {
        WebContent.smallDescription = smallDescription;
    }

    public static ArrayList<String> getTitleArray() {

        return titleArray;
    }

    public static void setTitleArray(ArrayList<String> titleArray) {
        WebContent.titleArray = titleArray;
    }

    private static ArrayList<String> titleArray;
    private static ArrayList<Drawable> imageArray;
    private static ArrayList<String> urlArray;
    private static String description;
    private static String rating;
    private static String requirement;
    private static ArrayList<String> downloadLinkArray;
    private static ArrayList<String> smallDescription;


    WebContent() {

        this.titleArray = null;
        this.imageArray = null;
        this.urlArray = null;
        this.description = null;
        this.rating = null;
        this.requirement = null;
        this.downloadLinkArray = null;
        this.smallDescription = null;

    }

}