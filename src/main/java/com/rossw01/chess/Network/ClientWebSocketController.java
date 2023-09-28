package com.rossw01.chess.Network;

import com.google.gson.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientWebSocketController {
    final URI SERVER_URI = new URI("ws://localhost:5000");
    private final ClientWebSocket client;

    public ClientWebSocketController() throws URISyntaxException {
        client = new ClientWebSocket(SERVER_URI);
        System.out.println("Socket Client Instantiated");
    }
    public void initialiseConnection() {
        System.out.printf("Initialising connection to server: %s %n", SERVER_URI);
        client.connect();
    }
    public ClientWebSocket getClient() {
        return client;
    }
    public void createRoom(String roomName) {
        JsonObject payload = new JsonObject();
        payload.addProperty("action", "create_room");
        payload.addProperty("room_name", roomName);
        client.send(payload.toString());
    }

    public void setName(String name) {
        JsonObject payload = new JsonObject();
        payload.addProperty("action", "set_name");
        payload.addProperty("name", name);
        client.send(payload.toString());
    }
    public void broadcast(String message) {
        JsonObject payload = new JsonObject();
        payload.addProperty("action", "broadcast");
        payload.addProperty("msg", message);
        client.send(payload.toString());
    }
    public ArrayList<String> getRooms() throws InterruptedException {
        JsonObject payload = new JsonObject();
        payload.addProperty("action", "get_rooms");
        client.send(payload.toString());
        Thread.sleep(1000);
        ArrayList<String> messageHistoryCopy = new ArrayList<>(client.getMessageHistory());
        // Get only the Strings in messageHistory that contain 'rooms'
        List<String> incomingRoomLists = messageHistoryCopy.stream().filter(item -> {
            JsonObject obj = JsonParser.parseString(item).getAsJsonObject();
            return obj.has("rooms");
        }).collect(Collectors.toList());
        if (incomingRoomLists.size() == 0) {
            return new ArrayList<String>(0);
        }
        // Get the latest room listing
        String latestRoomList = incomingRoomLists.get(incomingRoomLists.size() - 1);
        JsonArray roomsArray = JsonParser.parseString(latestRoomList).getAsJsonObject().get("rooms").getAsJsonArray();
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < roomsArray.size(); i++) {
            String name = roomsArray.get(i).toString();
            out.add(name.substring(1, name.length() - 1));
        }
        return out;
    }
}
