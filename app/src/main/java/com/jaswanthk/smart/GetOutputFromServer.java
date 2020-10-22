package com.jaswanthk.smart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JASWANTH K on 22-03-2018.
 */

public class GetOutputFromServer extends AsyncTask<String,String,String> {
    Context myc;
    ProgressDialog dialog;
    public AsyncResponse delegate = null;
    public GetOutputFromServer(AsyncResponse asyncResponse) {
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog= ProgressDialog.show(,"","uploading....",true);
    }

    @Override
    protected String doInBackground(String... strings) {
        String outputString=null;
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        try {
            url=new URL(strings[0]);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String result="";
            while ((result = reader.readLine())!= null)
                builder.append(result);
            Thread.sleep(5000);
            outputString=builder.toString().trim();

            inputStream.close();
            httpURLConnection.disconnect();

            return outputString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputString;
    }

    @Override
    protected void onPostExecute(String s) {
//        dialog.dismiss();
        delegate.processFinish(s);

    }
}
