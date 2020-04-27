package com.example.mobiilitietoliikenne_h2_t1;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataAsyncTask extends AsyncTask<String, Void, String> {

    private String stocksUrl;

    public GetDataAsyncTask(String url) {
        this.stocksUrl = url;
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
        String name = "";
        String price = "";
        try {
            URL url = new URL(stocksUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            httpData = Utilities.fromStream(in);

            JSONObject jsonObject = new JSONObject(httpData);
            JSONObject jsonObject2 = new JSONObject();

            for (int i =0 ; i <jsonObject.length(); i++) {
                jsonObject2 = jsonObject.getJSONObject("profile");
            }
            if (jsonObject2.length() < 1) {
                httpData = Utilities.ERROR_DATA_NOT_FOUND;
            }
            else {
                name = jsonObject2.getString("companyName");
                price = jsonObject2.getString("price");
                httpData = name + " : " + price;
            }
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
