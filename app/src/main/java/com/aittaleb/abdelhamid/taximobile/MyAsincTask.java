package com.aittaleb.abdelhamid.taximobile;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abdelhamid on 20/05/17.
 */

public class MyAsincTask extends AsyncTask<String,String,String>
{
    String newsData="";
    @Override
    protected void onPreExecute()
    {
        newsData ="";
    }

    @Override
    protected String doInBackground(String... params) {

        publishProgress("open Connection");

        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            publishProgress("Reading start");
            newsData = StreamToString(in);

            //Toast.makeText(getApplicationContext(),"dayza",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            publishProgress("cannot connect");
           // Toast.makeText(getApplicationContext(),"dayza",Toast.LENGTH_SHORT).show();


        }

        return null;

    }



    @Override
    protected void onProgressUpdate(String... params)
    {
        //Large.setText(params[0]);
        //Toast.makeText(getApplicationContext(),"dayza",Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onPostExecute(String result)
    {
        //Large.setText(newsData);


    }

    private String StreamToString(InputStream in) {
        BufferedReader bureader = new BufferedReader(new InputStreamReader(in));
        String Data="";
        String line="";
        try {

            while((line=bureader.readLine())!=null)
            {
                Data+=line;
            }
            in.close();


        }catch (Exception e)
        {
           // Toast.makeText(getApplicationContext(),"dayza",Toast.LENGTH_SHORT).show();

        }

        return Data;

    }


}