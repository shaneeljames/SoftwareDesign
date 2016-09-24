package com.example.tutor;

/**
 * Created by admin on 23-Sep-16.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//import static android.R.attr.password;

//new UploadToServer().execute("");


public class UploadToServer extends AsyncTask<Void, Void, Void> {

    Bitmap image ;
    String name ;
    private static final String SERVER_ADDRESS = "http://neural.net16.net/" ;

    public UploadToServer(Bitmap img , String n) {
     this.image = img ;
        this.name = n ;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream) ;
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

        ArrayList<NameValuePair> dataToSend = new ArrayList<>() ;
        dataToSend.add(new BasicNameValuePair("image",encodedImage));
        dataToSend.add(new BasicNameValuePair("name", name));

        HttpParams httpRequestParams = getHttpRequestParams() ;

        HttpClient client = new DefaultHttpClient(httpRequestParams) ;
        HttpPost post = new HttpPost( SERVER_ADDRESS + "Upload.php") ;
         try {
            post.setEntity(new UrlEncodedFormEntity(dataToSend));
            client.execute(post) ;
         } catch (Exception e)
         {
             e.printStackTrace();
         }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private HttpParams getHttpRequestParams()
    {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams,1000*30);
        HttpConnectionParams.setSoTimeout(httpRequestParams,1000*30);
        return  httpRequestParams ;
    }

}