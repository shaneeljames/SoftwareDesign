package com.example.jared.findmetutor;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by jared on 2016/08/04.
 */
public class send_php extends AsyncTask<String, String, String> {
    Activity parent;
    String URL;
    String data;

    public send_php(Activity par, String url2, String DATA){
        parent = par;
        URL = url2;
        data = DATA;

    }


    @Override
    protected String doInBackground(String... strings) {

        String message = null;
        try {
            message = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            // instantiate the URL object with the target URL of the resource to
            // request
            URL url = new URL(URL);

            // instantiate the HttpURLConnection with the URL object - A new
            // connection is opened every time by calling the openConnection
            // method of the protocol handler for this URL.
            // 1. This is the point where the connection is opened.
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            // set connection output to true
            connection.setDoOutput(true);
            // instead of a GET, we're going to send using method="POST"
            connection.setRequestMethod("POST");

            // instantiate OutputStreamWriter using the output stream, returned
            // from getOutputStream, that writes to this connection.
            // 2. This is the point where you'll know if the connection was
            // successfully established. If an I/O error occurs while creating
            // the output stream, you'll see an IOException.
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

            // write data to the connection. This is data that you are sending
            // to the server
            // 3. No. Sending the data is conducted here. We established the
            // connection with getOutputStream
            writer.write("message=" + message);

            // Closes this output stream and releases any system resources
            // associated with this stream. At this point, we've sent all the
            // data. Only the outputStream is closed at this point, not the
            // actual connection
            writer.close();
            // if there is a response code AND that response code is 200 OK, do
            // stuff in the first if block
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // OK
                return "OK!";
                // otherwise, if any other status code is returned, or no status
                // code is returned, do stuff in the else block
            } else {
                // Server returned HTTP error code.
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        TextView txt = (TextView) parent.findViewById(R.id.textView2);
        txt.setText(result);

    }

}

