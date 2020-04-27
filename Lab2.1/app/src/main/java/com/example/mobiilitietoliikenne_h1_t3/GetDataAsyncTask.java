package com.example.mobiilitietoliikenne_h1_t3;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataAsyncTask extends AsyncTask<String, Void, String> {

    private String dataUrl;

    public GetDataAsyncTask(String url) {
        this.dataUrl = url;
    }

    public interface ReporterInterface {
        void networkFetchDone(String data);
    }

    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }


    @Override
    protected String doInBackground(String... strings) {
        String httpData;
        try {
            URL url = new URL(dataUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            httpData = Utilities.fromStream(in);
        }
        catch (Exception e) {
            httpData = Utilities.ERROR_HTTP_REQUEST;
            e.printStackTrace();
        }
        return httpData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (callbackInterface != null) {
            callbackInterface.networkFetchDone(s);
        }
    }
}