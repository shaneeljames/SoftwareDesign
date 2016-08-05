package com.example.jared.findmetutor;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jared on 2016/08/04.
 */
public class register extends AsyncTask<String, String, String> {
    Activity parent;
    String URL;

    public register(Activity par, String url2){
        parent = par;
        URL = url2;

    }
    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{
            URL url = new URL(URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

            return buffer.toString();

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
            try{
                if(reader != null) {
                    reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        TextView txt = (TextView) parent.findViewById(R.id.textView2);
        txt.setText(result);

    }

}

