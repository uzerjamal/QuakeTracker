package com.uzerjamal.quaketracker;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeList>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<EarthquakeList> loadInBackground(){
        if(mUrl==null)
            return null;
        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}
