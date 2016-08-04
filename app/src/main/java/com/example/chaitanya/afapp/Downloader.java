package com.example.chaitanya.afapp;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;


//start the parsing using AsyncTask
public class Downloader extends AsyncTask<Void, Void, ArrayList> {

    //initialization of MainActivity
    MainActivity activity;

    //constructor of the Downloader class
    public Downloader(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        //creating the arraylist to get all the data from parcelabler class
        ArrayList<Promotions> promotions = new ArrayList<>();

        //url for the json
        String Url = "https://www.abercrombie.com/anf/nativeapp/Feeds/promotions.json";

        try {
            //initializating the URL object
            URL theUrl = new URL(Url);

            //initializating the StringBuilder object
            StringBuilder builder = new StringBuilder();

            //initializating the HttpsURLConnection object
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) theUrl.openConnection();

            //initializating the BufferedReader object
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));

            //for getting all the data from the json
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                builder.append(line);
            }
            String promotionJson = builder.toString();

            //main json object
            JSONObject jsonObject = new JSONObject(promotionJson);

            //first json array
            JSONArray jsonArray = jsonObject.getJSONArray("promotions");
            JSONObject firstObject = jsonArray.getJSONObject(0);

            //get values of all the items from the json object
            String desc = firstObject.getString("description");
            String footer = firstObject.getString("footer");
            String title = firstObject.getString("title");
            String image = firstObject.getString("image");

            //getting target url from the "button" object inside the first object
            JSONObject jsonObject1 = firstObject.getJSONObject("button");
            String url = jsonObject1.getString("target");

            //storing the data of teh first object in the parcelaber class which we are getting from json
            Promotions promotions1 = new Promotions(title, url, desc, footer, image);
            promotions.add(promotions1);

            //second json object
            JSONObject secondObject = jsonArray.getJSONObject(1);

            //getting the target url from the json array inside the second the json object
            JSONArray jsonArray1 = secondObject.getJSONArray("button");
            JSONObject btnObject = jsonArray1.getJSONObject(0);

            String url1 = btnObject.getString("target");
            String title1 = secondObject.getString("title");
            String desc1 = secondObject.getString("description");
            String footer1 = "";
            String image1 = secondObject.getString("image");

            //storing the data of the second object in the parcelaber class which we are getting from json
            Promotions promotions2 = new Promotions(title1,url1, desc1, footer1, image1);
            promotions.add(promotions2);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return promotions;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);

        //calling the method in the MainActivity to display the data from the json
        activity.sendPromotions(arrayList);

    }
}
