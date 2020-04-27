package com.example.mobiilitietoliikenne_h4_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SocketClientInterface {

    private SocketClient socketClient;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonOpenConnection).setOnClickListener(this);
        findViewById(R.id.buttonCloseConnection).setOnClickListener(this);
        findViewById(R.id.buttonSendMessage).setOnClickListener(this);

        handler = new Handler();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonOpenConnection) {
            openConnection();
        }
        else if (view.getId() == R.id.buttonCloseConnection) {
            closeConnection();
        }
        else if (view.getId() == R.id.buttonSendMessage) {
            sendMessage();
        }
    }

    private void openConnection() {
        if (socketClient != null && socketClient.isOpen()) {
            Toast.makeText(this, "Connection is already open.", Toast.LENGTH_SHORT).show();
        } else {
            try {
                socketClient = new SocketClient(new URI("wss://obscure-waters-98157.herokuapp.com"), this);
                socketClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeConnection() {
        if (socketClient == null || socketClient.isClosed()) {
            Toast.makeText(this, "Connection is already closed.", Toast.LENGTH_SHORT).show();
        }
        else {
            TextView textView = findViewById(R.id.textViewChat);
            textView.setText(null);
            socketClient.close();
        }
    }

    private void sendMessage() {
        if (socketClient != null && socketClient.isOpen()) {
            EditText editText = findViewById(R.id.editTextMessage);
            String message = editText.getText().toString();
            editText.setText(null);
            socketClient.send(message);
        }
        else {
            Toast.makeText(this, "Connection is closed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void messageReceived(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textViewChat);
                textView.append(message + "\n");
            }
        });
    }

    @Override
    public void connectionStatusChanged(final String status) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textViewConnectionStatus);
                textView.setText(status);
            }
        });
    }

    @Override
    public void actionOccurred(final String action) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textViewAction);
                textView.setText(action);
            }
        });
    }
}
