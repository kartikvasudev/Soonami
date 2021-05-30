package com.learning.Soonami;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EarthquakesDataLoader extends AsyncTaskLoader<ArrayList<Earthquakes>> {

    private static final String TAG = EarthquakesDataLoader.class.getSimpleName();
    private static String USGS_REQUEST_URL = null;

    public EarthquakesDataLoader(Context context,String url)
    {
        super(context);
        USGS_REQUEST_URL = url;
    }

    @Nullable
    @Override
    public ArrayList<Earthquakes> loadInBackground() {
        ArrayList<Earthquakes> earthquakes = null;
        try {
//            Thread.sleep(2000);
            URL url = new URL(USGS_REQUEST_URL);
            String jsonResponse = makeHttpRequest(url);
            earthquakes = extractEarthquakesFromJSON(jsonResponse);
        } catch (IOException | JSONException e) {
            Log.e(TAG,"Error Forming URL/Making HTTP Request");
        }
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG,"Error Reading from input stream of response");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private ArrayList<Earthquakes> extractEarthquakesFromJSON(String response) throws JSONException {
        return QueryUtils.extractEarthquakes(response);
    }
}
