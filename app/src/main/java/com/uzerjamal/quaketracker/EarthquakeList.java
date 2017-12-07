package com.uzerjamal.quaketracker;

public class EarthquakeList {

    private String magnitude;
    private String nearby;
    private String location;
    private String date;
    private String time;
    private String url;

    public EarthquakeList(String cMagnitude, String cNearby, String cLocation, String cDate, String cTime, String cUrl){
        magnitude = cMagnitude;
        nearby = cNearby;
        location = cLocation;
        date = cDate;
        time = cTime;
        url = cUrl;
    }

    public String GetMagnitude(){
        return magnitude;
    }

    public String GetNearBy(){
        return nearby;
    }

    public String GetLocation(){
        return location;
    }

    public String GetDate(){
        return date;
    }

    public String GetTime(){
        return time;
    }

    public String GetUrl(){
        return url + "#map";
    }
}