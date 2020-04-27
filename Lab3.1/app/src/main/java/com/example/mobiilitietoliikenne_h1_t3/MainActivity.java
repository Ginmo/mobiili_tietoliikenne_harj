package com.example.mobiilitietoliikenne_h1_t3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Handler handler;
    RequestQueue requestQueue;
    TextView textViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonGoUrl).setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        handler = new Handler();
        textViewUrl = findViewById(R.id.textViewUrl);
        textViewUrl.setMovementMethod(new ScrollingMovementMethod());
    }

    public void getUrl(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textViewUrl.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        requestQueue.add(stringRequest);
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getUrl(url);
                    }
                });
            }
        }
    }
}
