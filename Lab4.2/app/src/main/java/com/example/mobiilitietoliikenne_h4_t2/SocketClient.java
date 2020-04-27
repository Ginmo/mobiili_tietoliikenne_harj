package com.example.mobiilitietoliikenne_h4_t2;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface SocketClientInterface {
    void messageReceived(String message);
    void connectionStatusChanged(String status);
    void actionOccurred(String action);
}

public class SocketClient extends WebSocketClient {

    private SocketClientInterface observer;

    public SocketClient(URI serverUri, SocketClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("HARJ4", "onOpen");
        observer.connectionStatusChanged("Connection: open");
        observer.actionOccurred("Connection opened.");
    }

    @Override
    public void onMessage(String message) {
        Log.d("HARJ4", "message");
        observer.messageReceived(message);
        observer.actionOccurred("Message received.");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("HARJ4", "onClose");
        observer.connectionStatusChanged("Connection: closed");
        observer.actionOccurred("Connection closed.");
    }

    @Override
    public void onError(Exception ex) {
        Log.d("HARJ4", "onError");
        observer.actionOccurred("Error: "+ ex.toString());
    }

}
