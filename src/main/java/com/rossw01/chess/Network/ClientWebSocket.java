package com.rossw01.chess.Network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;

public class ClientWebSocket extends WebSocketClient {

    private final ArrayList<String> messageHistory = new ArrayList<>();
    public ArrayList<String> getMessageHistory() {
        return messageHistory;
    }
    public ClientWebSocket(URI serverUri) {
        super(serverUri);
    }
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("WebSocket connection opened");
    }
    @Override
    public void onMessage(String s) {
        messageHistory.add(s);
        System.out.println("Received message: " + s);
    }
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("WebSocket connection closed");
        System.out.printf("%s :: %s :: %s %n", i, s, b);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
