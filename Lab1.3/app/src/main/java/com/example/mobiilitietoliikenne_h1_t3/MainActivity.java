package com.example.mobiilitietoliikenne_h1_t3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonGoUrl).setOnClickListener(this);
        textViewUrl = findViewById(R.id.textViewUrl);
        textViewUrl.setMovementMethod(new ScrollingMovementMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void getUrl(String urlFromEditText) {
        try {
            URL url = new URL(urlFromEditText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utilities.fromStream(in);
            textViewUrl.setText(htmlText);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public String getUrlText() {
        EditText editText = findViewById(R.id.editTextUrl);
        String text = editText.getText().toString();
        return text;
    }

    @Override
    public void onClick(View view) {
        final String url = getUrlText();
        if (view.getId() == R.id.buttonGoUrl) {
            if (url != null && url.length() > 0) {
                getUrl(url);
            }
        }
    }
}
