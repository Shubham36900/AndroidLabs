package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar mProgressBar;
    AsyncTask<String, Integer, String> fetchWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        String uvURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

        /*if (fetchWeather != null) {
            fetchWeather.cancel(true);
        }*/
        fetchWeather = new ForecastQuery();
        fetchWeather.execute(new String[]{queryURL,uvURL});
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String uv = "";
        String min = "";
        String max = "";
        String currentTemp = "";
        Bitmap weatherIcon = null;

        @Override                       //Type 1
        protected String doInBackground(String... args) {
            String ret = null;
            // String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
            // String uvURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

            try {       // Connect to the server:
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;         //While not the end of the document:
                while((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT)
                {
                    switch(EVENT_TYPE)
                    {
                        case START_TAG:         //This is a start tag < ... >
                            String tagName = xpp.getName(); // What kind of tag?
                            if(tagName.equals("temperature"))
                            {
                                currentTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = xpp.getAttributeValue(null,"min");
                                publishProgress(50);
                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(75);
                            }
                            if(tagName.equals("weather"))
                            {
                                String iconValue = xpp.getAttributeValue(null,"icon");
                                Log.i("file","this is the file name we are looking for: " + iconValue + ".png");

                                FileInputStream fis;

                                if (fileExistance(iconValue)) {
                                    fis = openFileInput(iconValue + ".png");
                                    weatherIcon = BitmapFactory.decodeStream(fis);
                                    Log.i("file", "this file is from local.");
                                } else {
                                    URL urlIcon = new URL("http://openweathermap.org/img/w/" + iconValue + ".png");
                                    HttpURLConnection connection = (HttpURLConnection) urlIcon.openConnection();
                                    connection.connect();
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {
                                        weatherIcon = BitmapFactory.decodeStream(connection.getInputStream());
                                        Log.i("file", "this file is from url or online.");
                                        FileOutputStream outputStream = openFileOutput(iconValue + ".png", Context.MODE_PRIVATE);
                                        weatherIcon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    }
                                }
                                publishProgress(100);
                            }
                            break;
                        case END_TAG:           //This is an end tag: </ ... >
                            break;
                        case TEXT:              //This is text between tags < ... > Hello world </ ... >
                            break;
                    }
                    xpp.next(); // move the pointer to next XML element
                }
                //get url from String
                URL uvLink = new URL(args[1]);

                //make http connection
                HttpURLConnection uvConnection = (HttpURLConnection) uvLink.openConnection();
                //get result in Stream
                InputStream uvStream = uvConnection.getInputStream();
                //read Stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(uvStream, "UTF-8"), 8);
                //build String
                StringBuilder sb = new StringBuilder();

                //make Stream to String
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                //result of stream to String
                String result = sb.toString();
                //become JSON Object
                JSONObject json = new JSONObject(result);
                //get uv from value
                Double uvFloat = json.getDouble("value");
                uv = uvFloat.toString();

                return "Executed";
            }
            catch(MalformedURLException mfe){ ret = "Malformed URL exception"; }
            catch(FileNotFoundException e) { ret = "Can not find file."; }
            catch(IOException ioe)          { ret = "IO Exception. Is the Wifi connected?";}
            catch(XmlPullParserException pe){ ret = "XML Pull exception. The XML is not properly formed" ;}
            catch(JSONException je) { ret = "JSON exception."; }
            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Update GUI stuff only:
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(values[0]);
        }

        @Override                   //Type 3
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            //update GUI Stuff:

            ImageView weatherImage = findViewById(R.id.imageView3);
            weatherImage.setImageBitmap(weatherIcon);

            TextView cTemp = findViewById(R.id.currentTemp);
            cTemp.setText(cTemp.getText()+ " : " + currentTemp + "°C");

            TextView minTemp = findViewById(R.id.minTemp);
            minTemp.setText(minTemp.getText()+ " : " +  min + "°C");

            TextView maxTemp = findViewById(R.id.maxTemp);
            maxTemp.setText(maxTemp.getText() + " : " + max + "°C");

            TextView uvView = findViewById(R.id.uv);
            uvView.setText(uvView.getText() + " : " + uv + "°C");

            mProgressBar.setVisibility(View.INVISIBLE);

        }

        /**
         * Return if file exist or not
         * @param fname Filename
         * @return true if exist or false if not exist
         */
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

    }
}