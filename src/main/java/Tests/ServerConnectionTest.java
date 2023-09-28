package Tests;

import com.rossw01.chess.Network.ClientWebSocketController;

import java.net.URISyntaxException;

public class ServerConnectionTest {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        ClientWebSocketController clientController = new ClientWebSocketController();
        clientController.initialiseConnection();
        Thread.sleep(5000);
        clientController.setName("gary");
        clientController.createRoom("test room");
    }
}
