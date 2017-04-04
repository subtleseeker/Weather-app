package com.example.doom.weather;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by doom on 3/4/17.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... urls) {


        String result = "";
        URL url;
        HttpURLConnection urlConnection=null;


        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data= reader.read();

            while (data != -1 ){

                char current =(char) data;

                result+= current;

                data = reader.read();

            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }




        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            double temperature = Double.parseDouble(weatherData.getString("temp"));

            temperature = (int)(temperature - 273.15);

            String placeName = jsonObject.getString("name");


            MainActivity.temperatureTextView.setText(String.valueOf(temperature));

            MainActivity.placeTextView.setText(placeName);




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
