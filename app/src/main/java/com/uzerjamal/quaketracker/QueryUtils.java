package com.uzerjamal.quaketracker;


import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthquakeList} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<EarthquakeList> extractEarthquakes(String jsonData) {
        if(TextUtils.isEmpty(jsonData))
            return null;

        // Create an empty ArrayList that we can start adding earthquakes to
        List<EarthquakeList> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(jsonData);
            JSONArray featuresArray = root.getJSONArray("features");
            for (int i=0;i<featuresArray.length();i++){
                JSONObject featuresElement = featuresArray.getJSONObject(i);
                JSONObject earthquake = featuresElement.getJSONObject("properties");

                double mag = earthquake.getDouble("mag");
                DecimalFormat formatter = new DecimalFormat("0.0");

                String place = earthquake.getString("place");
                String nearby;
                String location;

                String url = earthquake.getString("url");

                if(place.contains(" of ")){
                    nearby = place.substring(0,place.indexOf(" of ")+4);
                    location = place.substring(place.indexOf(" of ")+4);
                }
                else{
                    nearby = "Near the";
                    location = place;
                }

                long date = earthquake.getLong("time");

                Date dateObject = new Date(date);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");

                String dateToDisplay = dateFormatter.format(dateObject);
                dateFormatter = new SimpleDateFormat("h:mm a");
                String timeToDisplay = dateFormatter.format(dateObject);

                earthquakes.add(new EarthquakeList(formatter.format(mag),nearby,location,dateToDisplay,timeToDisplay,url));
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }



        // Return the list of earthquakes
        return earthquakes;
    }

    public static List<EarthquakeList> fetchEarthquakeData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<EarthquakeList> earthquake = extractEarthquakes(jsonResponse);

        return earthquake;
    }

    private static URL createUrl(String stringURL) {
        URL url=null;
        try {
            url=new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";

        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(QueryUtils.class.getSimpleName(), "Error response code:" + urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
}
